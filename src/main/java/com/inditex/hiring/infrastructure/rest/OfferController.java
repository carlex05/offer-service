package com.inditex.hiring.infrastructure.rest;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inditex.hiring.infrastructure.rest.dto.OfferDto;


//import javax.validation.Valid;


@RestController
@RequestMapping(path = "/offers")
public class OfferController {

	
	

	@PostMapping
	public ResponseEntity<OfferDto> createOffer(@RequestBody @Valid OfferDto offerdto){
		return null;
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