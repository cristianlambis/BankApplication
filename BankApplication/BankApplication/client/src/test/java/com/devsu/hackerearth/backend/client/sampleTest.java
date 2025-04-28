package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
public class sampleTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

	private ClientService clientService = mock(ClientService.class);
	private ClientController clientController = new ClientController(clientService);

    @Test
    void createClientTest() {
        // Arrange
        ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        ClientDto createdClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        when(clientService.create(newClient)).thenReturn(createdClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.create(newClient);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClient, response.getBody()); 
    }

    @Test
    void testClientModel() {
        Client client = new Client();
        client.setId(1l);
        client.setName("Pedro");
        client.setDni("123456");
        client.setGender("M");
        client.setAge(30);
        client.setAddress("La esperanza");
        client.setPhone("123456789");
        client.setPassword("clave");
        client.setActive(true);

        assertEquals(1L, client.getId());
        assertEquals("Pedro", client.getName());
        assertEquals("123456", client.getDni());
        assertEquals("M", client.getGender());
        assertEquals(30, client.getAge());
        assertEquals("La esperanza", client.getAddress());
        assertEquals("123456789", client.getPhone());
        assertEquals("clave", client.getPassword());
        assertEquals(true, client.isActive());
    }

    @Test
    void shouldCreateClientSuccessfully() throws Exception {
        ClientDto clientDto = new ClientDto(
            null,
            "123456",
            "Pedro",
            "clave",
            "M",
            30,
            "La esperanza",
            "123456789",
            true
            
        );

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isCreated());
    }
}
