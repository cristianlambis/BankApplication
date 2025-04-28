package com.devsu.hackerearth.backend.account.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.client.RestTemplateClient;
import com.devsu.hackerearth.backend.account.exception.ApiException;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.mapper.TransactionMapper;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final RestTemplateClient restTemplateClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
            AccountService accountService, RestTemplateClient restTemplateClient) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.restTemplateClient = restTemplateClient;
    }

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream()
                .map(TransactionMapper::entityToDto)
                .collect(Collectors.toList());

    }

    @Override
    public TransactionDto getById(Long id) {
        Transaction transaction = findTransactionByIdOrThrow(id);
        return TransactionMapper.entityToDto(transaction);
    }

    @Transactional
    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        if (transactionDto.getAmount() < -0) {
            throw new ApiException("El monto debe ser mayor a 0", HttpStatus.BAD_REQUEST);
        }

        AccountDto accountDto = accountService.getById(transactionDto.getAccountId());
        if (accountDto == null) {
            throw new ApiException("Cuenta no encontrada con ID: " + transactionDto.getAccountId(),
                    HttpStatus.NOT_FOUND);
        }

        double currentBalance = accountDto.getInitialAmount();

        if (transactionDto.getType().equalsIgnoreCase("WITHDRAWAL")) {
            if (currentBalance < transactionDto.getAmount()) {
                throw new ApiException("Saldo no disponible", HttpStatus.PRECONDITION_FAILED);
            }
            currentBalance -= transactionDto.getAmount();
        } else if (transactionDto.getType().equalsIgnoreCase("DEPOSIT")) {
            currentBalance += transactionDto.getAmount();
        } else {
            throw new ApiException("Tipo de transaccion invalido", HttpStatus.BAD_REQUEST);
        }

        accountDto.setInitialAmount(currentBalance);
        accountService.update(accountDto.getId(), accountDto);

        transactionDto.setBalance(currentBalance);

        Transaction savedTransaction = saveTransactionSafe(TransactionMapper.dtoToEntity(transactionDto));

        return TransactionMapper.entityToDto(savedTransaction);
    }

    @Transactional
    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {

        List<Tuple> transactions = transactionRepository.findAllByClientIdAndDate(clientId,
                dateTransactionStart, dateTransactionEnd);

        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }

        String clientName = restTemplateClient.getClientNameById(clientId);

        return transactions.stream()
                .map(result -> new BankStatementDto(
                        result.get("date", Date.class),
                        (!clientName.isEmpty() ? clientName : result.get("clientId", Long.class).toString()),
                        result.get("accountNumber", String.class),
                        result.get("accountType", String.class),
                        result.get("initialAmount", Double.class),
                        result.get("isActive", Boolean.class),
                        result.get("transactionType", String.class),
                        result.get("amount", Double.class),
                        result.get("balance", Double.class)))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        Optional<Transaction> transactionOptional = transactionRepository.findLatestByAccountId(accountId);
        if (transactionOptional.isEmpty()) {
            return null;
        }
        return TransactionMapper.entityToDto(transactionOptional.get());
    }

    private Transaction findTransactionByIdOrThrow(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ApiException("Transaccion no encontrada", HttpStatus.NOT_FOUND));
    }

    private Transaction saveTransactionSafe(Transaction transaction) {
        try {
            return transactionRepository.save(transaction);
        } catch (Exception ex) {
            throw new ApiException("Error al guardar la transaccion", HttpStatus.PRECONDITION_FAILED);
        }
    }
}
