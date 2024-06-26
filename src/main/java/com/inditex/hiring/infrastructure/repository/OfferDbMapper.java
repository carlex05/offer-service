package com.inditex.hiring.infrastructure.repository;

import com.inditex.hiring.domain.model.Offer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface OfferDbMapper {

    static Offer toOffer(ResultSet rs, int rowNum) throws SQLException {
        return new Offer(
                rs.getLong("OFFER_ID"),
                rs.getInt("BRAND_ID"),
                rs.getTimestamp("START_DATE").toLocalDateTime(),
                rs.getTimestamp("END_DATE").toLocalDateTime(),
                rs.getLong("PRICE_LIST"),
                rs.getString("SIZE"),
                rs.getString("MODEL"),
                rs.getString("QUALITY"),
                rs.getInt("PRIORITY"),
                rs.getBigDecimal("PRICE"),
                rs.getString("CURR"));
    }

}
