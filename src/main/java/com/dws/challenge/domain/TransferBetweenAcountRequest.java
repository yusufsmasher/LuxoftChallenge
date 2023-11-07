package com.dws.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data

public class TransferBetweenAcountRequest {
	@NotNull
	@NotEmpty
	private String fromAccountID;
	
	@NotNull
	@NotEmpty
	private String toAccountID;
	
	@NotNull
	  @Min(value = 1, message = "Initial balance must be positive.")
	private BigDecimal amount;

}
