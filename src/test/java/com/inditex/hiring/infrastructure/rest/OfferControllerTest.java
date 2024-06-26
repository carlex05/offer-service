package com.inditex.hiring.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.hiring.infrastructure.rest.dto.OfferDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOffer_invalidBody() throws Exception  {
        var invalidOffer = new OfferDto(
                null,
                null,
                null,
                null,
                1L,
                "000100233",
                0,
                new BigDecimal("35.50"),
                "EUR"
        );

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOffer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.offerId").value("Mandatory value offerId"))
                .andExpect(jsonPath("$.brandId").value("Mandatory value brandId"))
                .andExpect(jsonPath("$.startDate").value("No empty value startDate"))
                .andExpect(jsonPath("$.endDate").value("No empty value endDate"));
    }

}