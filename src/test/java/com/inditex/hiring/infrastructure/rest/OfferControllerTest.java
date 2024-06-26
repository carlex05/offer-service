package com.inditex.hiring.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.infrastructure.rest.dto.OfferDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateOffer_invalidBody() throws Exception  {
        var invalidOffer = new OfferDto(
                null,
                null,
                null,
                null,
                1L,
                "000100233",
                0,
                new BigDecimal("35.50"),
                "EUR"
        );

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOffer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.offerId").value("Mandatory value offerId"))
                .andExpect(jsonPath("$.brandId").value("Mandatory value brandId"))
                .andExpect(jsonPath("$.startDate").value("No empty value startDate"))
                .andExpect(jsonPath("$.endDate").value("No empty value endDate"));
    }

    @Test
    void testCreateOffer_withoutBody() throws Exception {
        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateOffer_duplicated() throws Exception {
        OfferDto duplicateOffer = new OfferDto(
                1L,  // duplicate offerId
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0),
                LocalDateTime.of(2023, 12, 31, 23, 59),
                1L,
                "P1234",
                1,
                new BigDecimal("100.0"),
                "USD"
        );

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateOffer)))
                .andExpect(status().isConflict());
    }

    @Test
    void testCreateOffer_success() throws Exception {
        OfferDto validOffer = new OfferDto(
                2L,  // valid offerId
                2,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59),
                2L,
                "P1235",
                1,
                new BigDecimal("150.0"),
                "EUR"
        );

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOffer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.offerId").value(validOffer.offerId()))
                .andExpect(jsonPath("$.brandId").value(validOffer.brandId()))
                .andExpect(jsonPath("$.startDate").value(validOffer.startDate().toString()))
                .andExpect(jsonPath("$.endDate").value(validOffer.endDate().toString()))
                .andExpect(jsonPath("$.priceListId").value(validOffer.priceListId()))
                .andExpect(jsonPath("$.productPartnumber").value(validOffer.productPartnumber()))
                .andExpect(jsonPath("$.priority").value(validOffer.priority()))
                .andExpect(jsonPath("$.price").value(validOffer.price().doubleValue()))
                .andExpect(jsonPath("$.currencyIso").value(validOffer.currencyIso()));
    }

    @Test
    void testGetOfferById_withValidId() throws Exception {
        Offer offer = new Offer(
                1L,
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0),
                LocalDateTime.of(2023, 12, 31, 23, 59),
                1L,
                "P1234",
                1,
                new BigDecimal("100.0"),
                "USD"
        );

        mockMvc.perform(get("/offers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.offerId").value(offer.offerId()))
                .andExpect(jsonPath("$.brandId").value(offer.brandId()))
                .andExpect(jsonPath("$.startDate").value(offer.startDate().toString()))
                .andExpect(jsonPath("$.endDate").value(offer.endDate().toString()))
                .andExpect(jsonPath("$.priceListId").value(offer.priceListId()))
                .andExpect(jsonPath("$.productPartnumber").value(offer.productPartnumber()))
                .andExpect(jsonPath("$.priority").value(offer.priority()))
                .andExpect(jsonPath("$.price").value(offer.price().doubleValue()))
                .andExpect(jsonPath("$.currencyIso").value(offer.currencyIso()));
    }

    @Test
    void testGetOfferById_withInvalidId() throws Exception {

        mockMvc.perform(get("/offers/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}