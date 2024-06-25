package com.inditex.hiring.domain.service;

import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.port.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    OfferRepository repository;

    @Test
    void createOffer_withNullValue() {
        assertThrows(IllegalArgumentException.class, () -> new OfferService(null).createOffer(null));
    }

    @Test
    void createOffer_validOffer(){
        doNothing().when(repository).createOffer(any(Offer.class));
        var offer = createOffer();
        new OfferService(repository).createOffer(offer);
        verify(repository, times(1)).createOffer(offer);
    }

    Offer createOffer(){
        var initialDate = LocalDateTime.now();
        var finalDate = initialDate.plusDays(1);
        return new Offer(1000,
                2,
                initialDate,
                finalDate,
                10,
                "Number",
                1,
                BigDecimal.TEN,
                "ES");
    }
}