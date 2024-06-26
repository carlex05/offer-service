package com.inditex.hiring.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.infrastructure.rest.dto.OfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @BeforeEach
    void prepareDb() {
        jdbcTemplate.update("DELETE FROM Offer");
        jdbcTemplate.update(
                "INSERT INTO Offer (OFFER_ID, BRAND_ID, START_DATE, END_DATE, PRICE_LIST, SIZE, MODEL, QUALITY, PRIORITY, PRICE, CURR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                1L, 1, LocalDateTime.of(2023, 1, 1, 0, 0, 0), LocalDateTime.of(2023, 12, 31, 23, 59, 0), 1L, "00", "0100", "233", 1, new BigDecimal("100.0"), "USD"
        );
    }

    @Test
    void testCreateOffer_invalidBody() throws Exception {
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
                1L,
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0),
                LocalDateTime.of(2023, 12, 31, 23, 59),
                1L,
                "000100233",
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
                2L,
                2,
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59, 0),
                2L,
                "000200234",
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
                .andExpect(jsonPath("$.startDate").value(validOffer.startDate().format(formatter)))
                .andExpect(jsonPath("$.endDate").value(validOffer.endDate().format(formatter)))
                .andExpect(jsonPath("$.priceListId").value(validOffer.priceListId()))
                .andExpect(jsonPath("$.productPartnumber").value(validOffer.productPartnumber()))
                .andExpect(jsonPath("$.priority").value(validOffer.priority()))
                .andExpect(jsonPath("$.price").value(validOffer.price().doubleValue()))
                .andExpect(jsonPath("$.currencyIso").value(validOffer.currencyIso()));
    }

    @Test
    void testGetOfferById_withValidId() throws Exception {
        mockMvc.perform(get("/offers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.offerId").value(1L))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.startDate").value(LocalDateTime.of(2023, 1, 1, 0, 0).format(formatter)))
                .andExpect(jsonPath("$.endDate").value(LocalDateTime.of(2023, 12, 31, 23, 59).format(formatter)))
                .andExpect(jsonPath("$.priceListId").value(1L))
                .andExpect(jsonPath("$.productPartnumber").value("000100233"))
                .andExpect(jsonPath("$.priority").value(1))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.currencyIso").value("USD"));
    }

    @Test
    void testGetOfferById_withInvalidId() throws Exception {
        mockMvc.perform(get("/offers/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllOffers() throws Exception {
        OfferDto validOffer = new OfferDto(
                2L,  // valid offerId
                2,
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59),
                2L,
                "000200234",
                1,
                new BigDecimal("150.0"),
                "EUR"
        );

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOffer)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/offers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].offerId").value(1L))
                .andExpect(jsonPath("$[0].brandId").value(1))
                .andExpect(jsonPath("$[0].startDate").value(LocalDateTime.of(2023, 1, 1, 0, 0).format(formatter)))
                .andExpect(jsonPath("$[0].endDate").value(LocalDateTime.of(2023, 12, 31, 23, 59).format(formatter)))
                .andExpect(jsonPath("$[0].priceListId").value(1L))
                .andExpect(jsonPath("$[0].productPartnumber").value("000100233"))
                .andExpect(jsonPath("$[0].priority").value(1))
                .andExpect(jsonPath("$[0].price").value(100.0))
                .andExpect(jsonPath("$[0].currencyIso").value("USD"))
                .andExpect(jsonPath("$[1].offerId").value(2L))
                .andExpect(jsonPath("$[1].brandId").value(2))
                .andExpect(jsonPath("$[1].startDate").value(LocalDateTime.of(2024, 1, 1, 0, 0).format(formatter)))
                .andExpect(jsonPath("$[1].endDate").value(LocalDateTime.of(2024, 12, 31, 23, 59).format(formatter)))
                .andExpect(jsonPath("$[1].priceListId").value(2L))
                .andExpect(jsonPath("$[1].productPartnumber").value("000200234"))
                .andExpect(jsonPath("$[1].priority").value(1))
                .andExpect(jsonPath("$[1].price").value(150.0))
                .andExpect(jsonPath("$[1].currencyIso").value("EUR"));
    }

    @Test
    void testUpdateOffer_success() throws Exception {
        OfferDto updatedOffer = new OfferDto(
                1L,
                2,
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                LocalDateTime.of(2023, 12, 31, 23, 59, 0),
                2L,
                "000200235",
                2,
                new BigDecimal("200.0"),
                "EUR"
        );

        mockMvc.perform(put("/offers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOffer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.offerId").value(updatedOffer.offerId()))
                .andExpect(jsonPath("$.brandId").value(updatedOffer.brandId()))
                .andExpect(jsonPath("$.startDate").value(updatedOffer.startDate().format(formatter)))
                .andExpect(jsonPath("$.endDate").value(updatedOffer.endDate().format(formatter)))
                .andExpect(jsonPath("$.priceListId").value(updatedOffer.priceListId()))
                .andExpect(jsonPath("$.productPartnumber").value(updatedOffer.productPartnumber()))
                .andExpect(jsonPath("$.priority").value(updatedOffer.priority()))
                .andExpect(jsonPath("$.price").value(updatedOffer.price().doubleValue()))
                .andExpect(jsonPath("$.currencyIso").value(updatedOffer.currencyIso()));
    }

    @Test
    void testUpdateOffer_withMismatchedIds() throws Exception {
        OfferDto updatedOffer = new OfferDto(
                2L,  // mismatched offerId
                2,
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                LocalDateTime.of(2023, 12, 31, 23, 59, 0),
                2L,
                "000200235",
                2,
                new BigDecimal("200.0"),
                "EUR"
        );

        mockMvc.perform(put("/offers/1")  // path variable id is 1, but offerId in body is 2
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOffer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateOffer_withInvalidId() throws Exception {
        OfferDto updatedOffer = new OfferDto(
                999L,  // non-existent offerId
                2,
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                LocalDateTime.of(2023, 12, 31, 23, 59, 0),
                2L,
                "000200235",
                2,
                new BigDecimal("200.0"),
                "EUR"
        );

        mockMvc.perform(put("/offers/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOffer)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteOfferById_success() throws Exception {
        mockMvc.perform(delete("/offers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        List<OfferDto> offers = jdbcTemplate.query("SELECT * FROM Offer WHERE OFFER_ID = ?", new Object[]{1L}, (rs, rowNum) -> new OfferDto(
                rs.getLong("OFFER_ID"),
                rs.getInt("BRAND_ID"),
                rs.getTimestamp("START_DATE").toLocalDateTime(),
                rs.getTimestamp("END_DATE").toLocalDateTime(),
                rs.getLong("PRICE_LIST"),
                rs.getString("SIZE") + rs.getString("MODEL") + rs.getString("QUALITY"),
                rs.getInt("PRIORITY"),
                rs.getBigDecimal("PRICE"),
                rs.getString("CURR")
        ));
        assertEquals(0, offers.size());
    }

    @Test
    void testDeleteOfferById_nonExistent() throws Exception {
        mockMvc.perform(delete("/offers/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOffer_invalidPartnumberTooShort() throws Exception {
        OfferDto invalidOffer = new OfferDto(
                2L,
                2,
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59, 0),
                2L,
                "00023", // Too short
                1,
                new BigDecimal("150.0"),
                "EUR"
        );

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOffer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.productPartnumber").value("The value must be 9 characters"));
    }

    @Test
    void testCreateOffer_invalidPartnumberTooLong() throws Exception {
        OfferDto invalidOffer = new OfferDto(
                2L,
                2,
                LocalDateTime.of(2024, 1, 1, 0, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59, 0),
                2L,
                "0002002345678", // Too long
                1,
                new BigDecimal("150.0"),
                "EUR"
        );

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOffer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.productPartnumber").value("The value must be 9 characters"));
    }

}