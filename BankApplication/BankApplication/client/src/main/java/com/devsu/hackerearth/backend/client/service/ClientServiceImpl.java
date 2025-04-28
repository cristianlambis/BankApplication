package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.exception.ApiException;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.model.mapper.ClientMapper;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientDto> getAll() {
		return clientRepository.findAll().stream()
				.map(ClientMapper::entityToDto)
				.collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		return ClientMapper.entityToDto(findClientByIdOrThrow(id));
	}

	@Transactional
	@Override
	public ClientDto create(ClientDto clientDto) {
		validateClientDoesNotExist(clientDto.getDni());
		Client client = ClientMapper.dtoToEntity(clientDto);
		return ClientMapper.entityToDto(saveClient(client));
	}

	@Transactional
	@Override
	public ClientDto update(Long id, ClientDto clientDto) {
		Client client = findClientByIdOrThrow(id);

		applyIfChanged(clientDto.getDni(), client.getDni(), dni -> {
			validateClientDoesNotExist(dni);
			client.setDni(dni);

		});

		applyIfChanged(clientDto.getName(), client.getName(), client::setName);
		applyIfChanged(clientDto.getPassword(), client.getPassword(), client::setPassword);
		applyIfChanged(clientDto.getGender(), client.getGender(), client::setGender);

		if (clientDto.getAge() != client.getAge()) {
			client.setAge(clientDto.getAge());
		}

		applyIfChanged(clientDto.getAddress(), client.getAddress(), client::setAddress);
		applyIfChanged(clientDto.getPhone(), client.getPhone(), client::setPhone);

		if (clientDto.isActive() != client.isActive()) {
			client.setActive(clientDto.isActive());
		}

		return ClientMapper.entityToDto(saveClient(client));

	}

	@Transactional
	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		Client client = findClientByIdOrThrow(id);
		client.setActive(partialClientDto.isActive());
		return ClientMapper.entityToDto(saveClient(client));

	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		Client client = findClientByIdOrThrow(id);
		clientRepository.delete(client);

	}

	private Client findClientByIdOrThrow(Long id) {
		return clientRepository.findById(id)
				.orElseThrow(() -> new ApiException("Cliente no encontrado", HttpStatus.NOT_FOUND));

	}

	private Client saveClient(Client client) {
		try {
			return clientRepository.save(client);
		} catch (Exception ex) {
			throw new ApiException("Error al guardar el cliente", HttpStatus.PRECONDITION_FAILED);
		}

	}

	private void validateClientDoesNotExist(String dni) {
		if (clientRepository.existsByDni(dni)) {
			throw new ApiException("Ya existe un cliente con el DNI: " + dni, HttpStatus.BAD_REQUEST);
		}

	}

	private <T> void applyIfChanged(T newValue, T currentValue, java.util.function.Consumer<T> setter) {
		if (newValue != null && !newValue.equals(currentValue)) {
			setter.accept(newValue);
		}
	}

}
