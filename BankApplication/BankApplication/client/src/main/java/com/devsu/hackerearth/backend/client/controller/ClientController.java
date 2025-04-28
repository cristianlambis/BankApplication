package com.devsu.hackerearth.backend.client.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.OnCreate;
import com.devsu.hackerearth.backend.client.model.dto.OnUpdate;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
@Validated
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping
	public ResponseEntity<List<ClientDto>> getAll() {
		return ResponseEntity.ok(clientService.getAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> get(@PathVariable Long id) {
		return ResponseEntity.ok(clientService.getById(id));

	}

	@PostMapping
	public ResponseEntity<ClientDto> create(@Validated(OnCreate.class) @RequestBody ClientDto clientDto) {
		ClientDto created = clientService.create(clientDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);

	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientDto> update(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody ClientDto clientDto) {
		clientDto.setId(id);
		return ResponseEntity.ok(clientService.update(id, clientDto));

	}

	@PatchMapping("/{id}")
	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id, @RequestBody PartialClientDto partialClientDto) {
		return ResponseEntity.ok(clientService.partialUpdate(id, partialClientDto));

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clientService.deleteById(id);
		return ResponseEntity.noContent().build();

	}

}
