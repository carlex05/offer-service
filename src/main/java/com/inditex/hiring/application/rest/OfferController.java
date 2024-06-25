package com.inditex.hiring.application.rest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.inditex.hiring.application.dto.OfferDto;


import javax.validation.Valid;


@RestController
public class OfferController {

	
	
	//Crear
	@RequestMapping(value="/offer", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public boolean createOffer(@RequestBody @Valid OfferDto offerdto){
		return true;
	}

	//Borrar por id
	@RequestMapping(value="/offer/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public Optional<Long> deleteOfferById(@PathVariable("id") Long id){
		return null;
	}

	//Obtener por id
	@RequestMapping(value="/offer/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public OfferDto getOfferById(@PathVariable Long id){
		return null;
		
	}

	//Eliminar todas las ofertas
	@RequestMapping(value = "/offer", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteAllOffers() {
	

	}
	
	//Endopint para optener todas las ofertas
	@RequestMapping(value = "/offer", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<OfferDto> getAllOffers() {
		return null;

	}

	
}