package com.devsu.hackerearth.backend.account.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

	private Long id;

	@NotBlank(groups = OnCreate.class, message = "El campo 'number' es obligatorio")
	@Size(groups = { OnCreate.class,
			OnUpdate.class }, min = 5, max = 20, message = "El campo 'number' debe tener entre 5 y 20 caracteres")
	private String number;

	@NotBlank(groups = OnCreate.class, message = "El campo 'type' es obligatorio")
	@Pattern(groups = { OnCreate.class,
			OnUpdate.class }, regexp = "^(SAVINGS|CURRENT)$", message = "El campo 'type' debe ser 'SAVINGS' o 'CURRENT'")
	private String type;

	@NotNull(groups = OnCreate.class, message = "El campo 'initialAmount' es obligatorio")
	@Min(groups = { OnCreate.class,
			OnUpdate.class }, value = 0, message = "El campo 'initialAmount' debe iniciar minimo con '0.0'")
	@JsonProperty("initial_Amount")
	private double initialAmount;

	@JsonProperty("is_Active")
	private boolean isActive;

	@NotNull(groups = OnCreate.class, message = "El campo 'clientId' es obligatorio")
	@Min(groups = { OnCreate.class,
			OnUpdate.class }, value = 0, message = "El campo 'clientId' debe tener ID del cliente positivo")
	@JsonProperty("client_Id")
	private Long clientId;
}
