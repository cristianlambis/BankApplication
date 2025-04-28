package com.devsu.hackerearth.backend.account.model.mapper;


import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

public class TransactionMapper {

    public static TransactionDto entityToDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setAccountId(transaction.getAccountId());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setBalance(transaction.getBalance());
        transactionDto.setDate(transaction.getDate());
        transactionDto.setType(transaction.getType());
        return transactionDto;
    }

    public static Transaction dtoToEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(transactionDto.getAccountId());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setBalance(transactionDto.getBalance());
        transaction.setDate(transactionDto.getDate());
        transaction.setType(transactionDto.getType());

        return transaction;
    }
}
