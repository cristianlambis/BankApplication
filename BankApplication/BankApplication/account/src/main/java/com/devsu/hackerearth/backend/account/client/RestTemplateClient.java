package com.devsu.hackerearth.backend.account.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devsu.hackerearth.backend.account.model.dto.ClientDto;

@Service
public class RestTemplateClient {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateClient.class);
    private static final String CLIENT_SERVICE_URL = "http://localhost:8001/api/clients/{clientId}";

    private final RestTemplate restTemplate;

    @Autowired
    public RestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean existsClient(Long clientId) {
        return getClientById(clientId) != null;
    }

    public String getClientNameById(Long clientId) {
        ClientDto client = getClientById(clientId);
        return client != null ? client.getName() : "";
    }

    private ClientDto getClientById(Long clientId) {
        try {
            ResponseEntity<ClientDto> response = restTemplate.getForEntity(CLIENT_SERVICE_URL, ClientDto.class,
                    clientId);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else if (response.getStatusCode().is4xxClientError()) {
                logger.error("Unexpected error while retrieving client with ID {}: HTTP {}", clientId,
                        response.getStatusCodeValue());
            }
        } catch (Exception ex) {
            logger.error("Error odtaining client with ID {}: {}", clientId, ex.getMessage());
        }
        return null;
    }

}
