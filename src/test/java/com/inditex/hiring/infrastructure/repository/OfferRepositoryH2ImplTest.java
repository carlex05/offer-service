package com.inditex.hiring.infrastructure.repository;

import com.inditex.hiring.domain.exception.DuplicateOfferIdException;
import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.port.OfferRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OfferRepositoryH2ImplTest {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    void prepareDb(){

    }

    @Test
    void testCreateOffer_duplicateId() {
        Offer offer = new Offer(
                1L,
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0),
                LocalDateTime.of(2023, 12, 31, 23, 59),
                1L,
                "P1235",
                1,
                new BigDecimal("150.0"),
                "USD"
        );

        assertThrows(DuplicateOfferIdException.class, () -> offerRepository.createOffer(offer));

    }

    @Test
    void testCreateOffer_success() {
        Offer offer = new Offer(
                2L,
                1,
                LocalDateTime.of(2023, 1, 1, 0, 0),
                LocalDateTime.of(2023, 12, 31, 23, 59),
                1L,
                "P1235",
                1,
                new BigDecimal("150.0"),
                "USD"
        );

        offerRepository.createOffer(offer);

        List<Offer> offers = jdbcTemplate.query("SELECT * FROM Offer", (rs, rowNum) -> new Offer(
                rs.getLong("OFFER_ID"),
                rs.getInt("BRAND_ID"),
                rs.getTimestamp("START_DATE").toLocalDateTime(),
                rs.getTimestamp("END_DATE").toLocalDateTime(),
                rs.getLong("PRICE_LIST"),
                rs.getString("PARTNUMBER"),
                rs.getInt("PRIORITY"),
                rs.getBigDecimal("PRICE"),
                rs.getString("CURR")
        ));
        assertEquals(2, offers.size());
    }

    @Test
    void testFindById_withValidId() {
        Optional<Offer> offer = offerRepository.findById(1L);

        assertTrue(offer.isPresent());
        assertEquals(1L, offer.get().offerId());
    }

    @Test
    void testFindById_withInvalidId() {
        Optional<Offer> offer = offerRepository.findById(999L);

        assertFalse(offer.isPresent());
    }
}