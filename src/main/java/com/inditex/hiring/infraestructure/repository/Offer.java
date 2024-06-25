package com.inditex.hiring.infraestructure.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;




public class Offer {


	private Long offerId;


	private Integer brandId;


	private Timestamp startDate;


	private Timestamp endDate;


	private Integer priceListId;


	private String productPartnumber;


	private Integer priority;


	private BigDecimal price;


	private String currencyIso;

	public Offer() {
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	
	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Integer getPriceListId() {
		return priceListId;
	}

	public void setPriceListId(Integer priceListId) {
		this.priceListId = priceListId;
	}

	public String getProductPartnumber() {
		return productPartnumber;
	}

	public void setProductPartnumber(String productPartnumber) {
		this.productPartnumber = productPartnumber;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCurrencyIso() {
		return currencyIso;
	}

	public void setCurrencyIso(String currencyIso) {
		this.currencyIso = currencyIso;
	}
	
	
}
