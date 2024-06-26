package com.inditex.hiring.infrastructure.repository;

import com.inditex.hiring.domain.exception.DuplicateOfferIdException;
import com.inditex.hiring.domain.exception.NoSuchResourceFoundException;
import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.port.OfferRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Offer> findById(long id) {
        List<Offer> offers = jdbcTemplate.query(
                "SELECT * FROM Offer WHERE OFFER_ID = ?",
                new Object[]{id},
                OfferDbMapper::toOffer
        );
        return offers.stream().findFirst();
    }

    @Override
    public List<Offer> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM Offer",
                OfferDbMapper::toOffer
        );
    }

    @Override
    public void updateOffer(Offer offer) {
        jdbcTemplate.update(
                "UPDATE Offer SET BRAND_ID = ?, START_DATE = ?, END_DATE = ?, PRICE_LIST = ?, PARTNUMBER = ?, PRIORITY = ?, PRICE = ?, CURR = ? WHERE OFFER_ID = ?",
                offer.brandId(),
                offer.startDate(),
                offer.endDate(),
                offer.priceListId(),
                offer.productPartnumber(),
                offer.priority(),
                offer.price(),
                offer.currencyIso(),
                offer.offerId()
        );
    }

    @Override
    public void deleteOfferById(long id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM Offer WHERE OFFER_ID = ?", id);

        if (rowsAffected == 0) {
            throw new NoSuchResourceFoundException("Offer with ID " + id + " does not exist.");
        }
    }
}
