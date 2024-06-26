package com.inditex.hiring.infrastructure.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.service.OfferService;
import com.inditex.hiring.infrastructure.rest.exception.NoSuchResourceFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inditex.hiring.infrastructure.rest.dto.OfferDto;


@AllArgsConstructor
@RestController
@RequestMapping("/offers")
public class OfferController {

	private final OfferService offerService;

	@PostMapping
	public ResponseEntity<OfferDto> createOffer(@RequestBody @Valid OfferDto offerDto){
		Offer offer = OfferMapper.toDomain(offerDto);
		offerService.createOffer(offer);
		return ResponseEntity.created(URI.create("/offers/" + offerDto.offerId()))
				.body(offerDto);
	}

	@RequestMapping(value="/offer/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public Optional<Long> deleteOfferById(@PathVariable("id") Long id){
		return null;
	}

	@GetMapping("/{id}")
	public ResponseEntity<OfferDto> getOfferById(@PathVariable Long id){
		var offer = offerService.getOfferById(id);
		if(offer.isEmpty())
			throw new NoSuchResourceFoundException("Offer with id " + id + " not found");
		OfferDto offerDto = OfferMapper.toDto(offer.get());
		return ResponseEntity.ok(offerDto);
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