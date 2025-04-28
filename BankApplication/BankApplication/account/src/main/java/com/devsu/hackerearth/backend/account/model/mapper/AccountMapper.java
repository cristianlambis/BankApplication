package com.devsu.hackerearth.backend.account.model.mapper;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;

public class AccountMapper {
    public static AccountDto entityToDto(Account account) {
        
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setNumber(account.getNumber());
        accountDto.setType(account.getType());
        accountDto.setInitialAmount(account.getInitialAmount());
        accountDto.setActive(account.isActive());
        accountDto.setClientId(account.getClientId());
        return accountDto;
    }

    public static Account dtoToEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setId(accountDto.getId());
        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());
        return account;
    }

}
