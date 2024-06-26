package com.inditex.hiring.domain.port;

import com.inditex.hiring.domain.model.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    void createOffer(Offer offer);

    Optional<Offer> findById(long id);

    List<Offer> findAll();

    void updateOffer(Offer offer);
}
