package com.inditex.hiring.infrastructure.repository;

import com.inditex.hiring.domain.exception.DuplicateOfferIdException;
import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.port.OfferRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OfferRepositoryH2Impl implements OfferRepository {


    private final JdbcTemplate jdbcTemplate;

    public OfferRepositoryH2Impl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createOffer(Offer offer) {
        try {
            jdbcTemplate.update(
                    "INSERT INTO Offer (OFFER_ID, BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PARTNUMBER, PRIORITY, PRICE, CURR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    offer.offerId(),
                    offer.brandId(),
                    offer.startDate(),
                    offer.endDate(),
                    offer.priceListId(),
                    offer.productPartnumber(),
                    offer.priority(),
                    offer.price(),
                    offer.currencyIso()
            );
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateOfferIdException("An offer with ID " + offer.offerId() + " already exists.");
        }
    }
}
