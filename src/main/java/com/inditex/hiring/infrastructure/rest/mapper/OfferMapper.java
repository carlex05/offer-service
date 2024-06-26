package com.inditex.hiring.infrastructure.rest.mapper;

import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.infrastructure.rest.dto.OfferDto;


public interface OfferMapper {

    static OfferDto toDto(Offer offer) {
        return new OfferDto(
                offer.offerId(),
                offer.brandId(),
                offer.startDate(),
                offer.endDate(),
                offer.priceListId(),
                offer.size() + offer.model() + offer.quality(), // Combine the parts back to productPartnumber
                offer.priority(),
                offer.price(),
                offer.currencyIso()
        );
    }

    static Offer toDomain(OfferDto offerDto) {
        String partnumber = offerDto.productPartnumber();
        String size = partnumber.substring(0, 2);
        String model = partnumber.substring(2, 6);
        String quality = partnumber.substring(6, 9);

        return new Offer(
                offerDto.offerId(),
                offerDto.brandId(),
                offerDto.startDate(),
                offerDto.endDate(),
                offerDto.priceListId(),
                size,
                model,
                quality,
                offerDto.priority(),
                offerDto.price(),
                offerDto.currencyIso()
        );
    }

}
