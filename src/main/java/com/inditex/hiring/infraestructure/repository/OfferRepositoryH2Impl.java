package com.inditex.hiring.infraestructure.repository;

import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.port.OfferRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OfferRepositoryH2Impl implements OfferRepository {

    @Override
    public void createOffer(Offer offer) {

    }
}
