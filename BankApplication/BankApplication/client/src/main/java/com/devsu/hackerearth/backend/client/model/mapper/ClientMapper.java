package com.devsu.hackerearth.backend.client.model.mapper;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;

public class ClientMapper {

    public static Client dtoToEntity(ClientDto clientDto) {
        
        Client client = new Client();
        client.setActive(clientDto.isActive());
        client.setPassword(clientDto.getPassword());
        client.setName(clientDto.getName());
        client.setAddress(clientDto.getAddress());
        client.setAge(clientDto.getAge());
        client.setDni(clientDto.getDni());
        client.setGender(clientDto.getGender());
        client.setPhone(clientDto.getPhone());
        return client;
    }

    public static ClientDto entityToDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getDni(),
                client.getName(),
                client.getPassword(),
                client.getGender(),
                client.getAge(),
                client.getAddress(),
                client.getPhone(),
                client.isActive());
    }

}