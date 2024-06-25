package com.inditex.hiring.domain.service;

import static org.junit.jupiter.api.Assertions.*;
class OfferServiceTest {

    @org.junit.jupiter.api.Test
    void createOffer_withNullValue() {
        assertThrows(IllegalArgumentException.class, () -> new OfferService().createOffer(null));
    }
}