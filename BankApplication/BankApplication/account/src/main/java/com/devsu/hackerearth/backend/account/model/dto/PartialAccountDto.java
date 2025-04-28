package com.devsu.hackerearth.backend.account.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartialAccountDto {

	@JsonProperty("is_Active")
	private boolean isActive;
}
