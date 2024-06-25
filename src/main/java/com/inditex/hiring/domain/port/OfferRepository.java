package com.inditex.hiring.domain.port;

import com.inditex.hiring.domain.model.Offer;

public interface OfferRepository {

    void createOffer(Offer offer);

}
