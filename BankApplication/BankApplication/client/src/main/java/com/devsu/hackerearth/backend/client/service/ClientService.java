package com.devsu.hackerearth.backend.client.service;

import java.util.List;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;

public interface ClientService {

	List<ClientDto> getAll();
	ClientDto getById(Long id);
	ClientDto create(ClientDto clientDto);
	ClientDto update(Long id, ClientDto clientDto);
	ClientDto partialUpdate(Long id, PartialClientDto partialClientDto);
	void deleteById(Long id);
}
