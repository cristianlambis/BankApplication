package com.devsu.hackerearth.backend.account.service;

import java.util.List;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;

public interface AccountService {

    List<AccountDto> getAll();

	AccountDto getById(Long id);

	AccountDto create(AccountDto accountDto);

	AccountDto update(Long id, AccountDto accountDto);

	AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto);

	void deleteById(Long id);

	List<AccountDto> getByClientId(Long clientId);
}
