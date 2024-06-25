package com.inditex.hiring.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Offer (long offerId,
                     int brandId,
                     LocalDateTime startDate,
                     LocalDateTime endDate,
                     long priceListId,
                     String productPartnumber,
                     int priority,
                     BigDecimal price,
                     String currencyIso){
}
