package com.devsu.hackerearth.backend.account.model.dto;


import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

	private Long id;

	private Date date;

	@NotBlank(groups = OnCreate.class, message = "El campo 'type' es obligatorio")
	@Pattern(groups = { OnCreate.class,
			OnUpdate.class }, regexp = "^(WITHDRAWAL|DEPOSIT)$", message = "El campo 'type' debe ser 'WITHDRAWAL' o 'DEPOSIT'")
	private String type;

	@NotNull(groups = OnCreate.class, message = "El campo 'amount' es obligatorio")
	@Min(groups = { OnCreate.class, OnUpdate.class }, value = 1, message = "El campo 'amount' debe ser mayor que '0'")
	private double amount;

	private double balance;

	@NotNull(groups = OnCreate.class, message = "El campo 'accountId' es obligatorio")
	@Min(groups = { OnCreate.class, OnUpdate.class }, value = 1, message = "El campo 'accountId' debe ser un valor positivo")
	private Long accountId;


}
