package com.devsu.hackerearth.backend.client.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

	private Long id;

	@NotBlank(groups = OnCreate.class, message = "El campo 'dni' es obligatorio")
	@Size(groups = { OnCreate.class, OnUpdate.class }, min = 5, max = 20, message = "El campo 'dni' debe tener entre 5 y 20 caracteres")
	private String dni;

	@NotBlank(groups = OnCreate.class, message = "El campo 'name' es obligatorio")
	@Size(groups = { OnCreate.class, OnUpdate.class }, min = 2, max = 100, message = "El campo 'name' debe tener entre 2 y 100 caracteres")
	private String name;

	@NotBlank(groups = OnCreate.class, message = "El campo 'password' es obligatorio")
	@Size(groups = { OnCreate.class, OnUpdate.class }, min = 5, max = 20, message = "El campo 'password' debe tener entre 5 y 20 caracteres")
	private String password;

	@Size(groups = { OnCreate.class, OnUpdate.class }, max = 1, message = "El campo 'gender' debe tener maximo 1 caracter 'M' o 'F'")
	@Pattern(groups = { OnCreate.class, OnUpdate.class }, regexp = "^(M|F)$", message = "El campo 'gender' debe ser 'M' o 'F'")
	private String gender;

	@Min(groups = {OnCreate.class, OnUpdate.class }, value = 0, message = "El campo 'age' debe ser positivo")
	private int age;

	@Size(groups = { OnCreate.class, OnUpdate.class }, max = 100, message = "El campo 'address' debe tener maximo 100 caracteres")
	private String address;

	@NotBlank(groups = OnCreate.class, message = "El campo 'phone' es obligatorio")
	@Size(groups = { OnCreate.class, OnUpdate.class }, min = 5, max = 20, message = "El campo 'phone' debe tener entre 5 y 20 caracteres")
	private String phone;

	private boolean isActive;
	
}
