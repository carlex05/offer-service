package com.inditex.hiring.domain.service;

import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.port.OfferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository repository;

    public void createOffer(Offer offer){
        if(offer == null)
            throw new IllegalArgumentException("An offer is required");
        repository.createOffer(offer);
    }

    public Offer getOfferById(long id){
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found with id: " + id));
    }
}
