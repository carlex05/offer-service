package com.inditex.hiring.infrastructure.repository;

import com.inditex.hiring.domain.exception.DuplicateOfferIdException;
import com.inditex.hiring.domain.exception.NoSuchResourceFoundException;
import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.port.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class OfferRepositoryH2ImplTest {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void prepareDb() {
        jdbcTemplate.update("DELETE FROM Offer");
        jdbcTemplate.update(
                "INSERT INTO Offer (OFFER_ID, BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PARTNUMBER, PRIORITY, PRICE, CURR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                1L, 1, LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023, 12, 31, 23, 59), 1L, "P1234", 1, new BigDecimal("100.0"), "USD"
        );
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

    @Test
    void testFindAll() {
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

        List<Offer> offers = offerRepository.findAll();
        assertEquals(2, offers.size());
    }

    @Test
    void testUpdateOffer_success() {
        Offer offer = new Offer(
                1L,
                2,  // updated brandId
                LocalDateTime.of(2023, 1, 1, 0, 0),
                LocalDateTime.of(2023, 12, 31, 23, 59),
                2L,  // updated priceListId
                "P1235",  // updated partnumber
                2,  // updated priority
                new BigDecimal("200.0"),  // updated price
                "EUR"  // updated currency
        );

        offerRepository.updateOffer(offer);

        List<Offer> offers = jdbcTemplate.query("SELECT * FROM Offer WHERE OFFER_ID = ?", new Object[]{1L}, (rs, rowNum) -> new Offer(
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
        assertEquals(1, offers.size());
        Offer updatedOffer = offers.get(0);
        assertEquals(2, updatedOffer.brandId());
        assertEquals(2L, updatedOffer.priceListId());
        assertEquals("P1235", updatedOffer.productPartnumber());
        assertEquals(2, updatedOffer.priority());
        assertTrue(new BigDecimal("200.0").compareTo(updatedOffer.price()) == 0);
        assertEquals("EUR", updatedOffer.currencyIso());
    }

    @Test
    void testDeleteOfferById_success() {
        offerRepository.deleteOfferById(1L);

        List<Offer> offers = jdbcTemplate.query("SELECT * FROM Offer WHERE OFFER_ID = ?", new Object[]{1L}, (rs, rowNum) -> new Offer(
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
        assertEquals(0, offers.size());
    }

    @Test
    void testDeleteOfferById_nonExistent() {
        assertThrows(NoSuchResourceFoundException.class, () -> offerRepository.deleteOfferById(999L));
    }

}