package com.inditex.hiring.infrastructure.rest.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record OfferDto(
		@NotNull(message = "Mandatory value offerId") Long offerId,
		@NotNull(message = "Mandatory value brandId") Integer brandId,
		@NotNull(message = "No empty value startDate") LocalDateTime startDate,
		@NotNull(message = "No empty value endDate") LocalDateTime endDate,
		Long priceListId,
		String productPartnumber,
		Integer priority,
		BigDecimal price,
		String currencyIso
) implements Serializable {
	@Serial
	private static final long serialVersionUID = 448171649369562796L;
}