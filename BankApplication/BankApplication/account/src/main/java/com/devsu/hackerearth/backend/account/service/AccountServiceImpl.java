package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.client.RestTemplateClient;
import com.devsu.hackerearth.backend.account.exception.ApiException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.model.mapper.AccountMapper;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RestTemplateClient restTemplateClient;

    public AccountServiceImpl(AccountRepository accountRepository,
            RestTemplateClient restTemplateClient) {
        this.accountRepository = accountRepository;
        this.restTemplateClient = restTemplateClient;
    }

    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream()
                .map(AccountMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        Account account = findAccountById(id);
        return AccountMapper.entityToDto(account);
    }

    @Transactional
    @Override
    public AccountDto create(AccountDto accountDto) {
        validateAccountNumberNotExists(accountDto.getNumber());

        if (!restTemplateClient.existsClient(accountDto.getClientId())) {
            throw new ApiException("Cliente no encontrado con ID: " + accountDto.getClientId(), HttpStatus.NOT_FOUND);
        }

        Account account = AccountMapper.dtoToEntity(accountDto);
        return AccountMapper.entityToDto(accountRepository.save(account));
    }

    @Transactional
    @Override
    public AccountDto update(Long id, AccountDto accountDto) {
        Account account = findAccountById(id);

        applyIfChanged(accountDto.getNumber(), account.getNumber(), newNumber -> {
            validateAccountNumberNotExists(newNumber);
            account.setNumber(newNumber);
        });

        applyIfChanged(accountDto.getType(), account.getType(), account::setType);
        applyIfChanged(accountDto.getInitialAmount(), account.getInitialAmount(), account::setInitialAmount);
        applyIfChanged(accountDto.getClientId(), account.getClientId(), account::setClientId);

        if (accountDto.isActive() != account.isActive()) {
            account.setActive(accountDto.isActive());
        }

        return AccountMapper.entityToDto(accountRepository.save(account));
    }

    @Transactional
    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        Account account = findAccountById(id);
        account.setActive(partialAccountDto.isActive());

        return AccountMapper.entityToDto(accountRepository.save(account));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Account account = findAccountById(id);
        accountRepository.delete(account);
    }

    @Override
    public List<AccountDto> getByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId).stream()
                .map(AccountMapper::entityToDto)
                .collect(Collectors.toList());
    }

    private Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ApiException("Cuenta no encontrada", HttpStatus.NOT_FOUND));
    }

    private void validateAccountNumberNotExists(String number) {
        if (accountRepository.existsByNumber(number)) {
            throw new ApiException("Ya existe una cuenta con numero" + number, HttpStatus.BAD_REQUEST);
        }
    }

    private <T> void applyIfChanged(T newValue, T currentValue, java.util.function.Consumer<T> setter) {
        if (newValue != null && !newValue.equals(currentValue)) {
            setter.accept(newValue);
        }
    }

}
