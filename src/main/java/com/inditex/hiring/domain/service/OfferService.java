package com.inditex.hiring.domain.service;

import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.port.OfferRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OfferService {

    private final OfferRepository repository;

    void createOffer(Offer offer){
        if(offer == null)
            throw new IllegalArgumentException("An offer is required");
    }
}
