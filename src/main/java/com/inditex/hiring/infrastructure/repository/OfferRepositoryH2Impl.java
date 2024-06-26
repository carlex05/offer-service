package com.inditex.hiring.infrastructure.repository;

import com.inditex.hiring.domain.exception.DuplicateOfferIdException;
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
                (rs, rowNum) -> new Offer(
                        rs.getLong("OFFER_ID"),
                        rs.getInt("BRAND_ID"),
                        rs.getTimestamp("START_DATE").toLocalDateTime(),
                        rs.getTimestamp("END_DATE").toLocalDateTime(),
                        rs.getLong("PRICE_LIST"),
                        rs.getString("PARTNUMBER"),
                        rs.getInt("PRIORITY"),
                        rs.getBigDecimal("PRICE"),
                        rs.getString("CURR")
                )
        );
        return offers.stream().findFirst();
    }
}
