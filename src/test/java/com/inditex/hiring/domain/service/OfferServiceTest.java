package com.inditex.hiring.domain.service;

import com.inditex.hiring.domain.exception.NoSuchResourceFoundException;
import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.port.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Test
    void getOfferById_withValidId() {
        var offer = createOffer();
        when(repository.findById(1000L)).thenReturn(Optional.of(offer));

        OfferService offerService = new OfferService(repository);
        var foundOffer = offerService.getOfferById(1000L);

        assertTrue(foundOffer.isPresent());
        assertEquals(1000L, foundOffer.get().offerId());
        verify(repository, times(1)).findById(1000L);
    }

    @Test
    void getOfferById_withInvalidId() {
        when(repository.findById(1000L)).thenReturn(Optional.empty());

        OfferService offerService = new OfferService(repository);
        var notFoundOffer = offerService.getOfferById(1000L);
        assertTrue(notFoundOffer.isEmpty());
        verify(repository, times(1)).findById(1000L);
    }

    @Test
    void getAllOffers() {
        var offer1 = createOffer();
        var offer2 = createOffer();
        when(repository.findAll()).thenReturn(List.of(offer1, offer2));

        OfferService offerService = new OfferService(repository);
        var offers = offerService.getAllOffers();

        assertEquals(2, offers.size());
        assertEquals(offer1, offers.get(0));
        assertEquals(offer2, offers.get(1));
        verify(repository, times(1)).findAll();
    }

    @Test
    void updateOffer_withNullValue() {
        assertThrows(IllegalArgumentException.class, () -> new OfferService(null).updateOffer(null));
    }
    @Test
    void updateOffer_withNonExistentOffer() {
        var offer = createOffer();
        when(repository.findById(offer.offerId())).thenReturn(Optional.empty());

        OfferService offerService = new OfferService(repository);
        assertThrows(NoSuchResourceFoundException.class, () -> offerService.updateOffer(offer));
    }

    @Test
    void updateOffer_validOffer() {
        var offer = createOffer();
        when(repository.findById(offer.offerId())).thenReturn(Optional.of(offer));
        doNothing().when(repository).updateOffer(any(Offer.class));

        OfferService offerService = new OfferService(repository);
        offerService.updateOffer(offer);

        verify(repository, times(1)).updateOffer(offer);
    }

}