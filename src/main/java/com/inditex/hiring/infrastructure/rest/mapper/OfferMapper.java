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
                offer.productPartnumber(),
                offer.priority(),
                offer.price(),
                offer.currencyIso()
        );
    }

    static Offer toDomain(OfferDto offerDto) {
        return new Offer(
                offerDto.offerId(),
                offerDto.brandId(),
                offerDto.startDate(),
                offerDto.endDate(),
                offerDto.priceListId(),
                offerDto.productPartnumber(),
                offerDto.priority(),
                offerDto.price(),
                offerDto.currencyIso()
        );
    }

}
