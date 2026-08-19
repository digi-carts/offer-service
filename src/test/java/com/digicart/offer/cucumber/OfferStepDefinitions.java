package com.digicart.offer.cucumber;

import com.digicart.offer.entity.Offer;
import com.digicart.offer.service.OfferService;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.when;

public class OfferStepDefinitions {
    @Autowired
    OfferService offerService;

    @Before
    public void stubs() {
        when(offerService.findAll()).thenReturn(List.of(new Offer()));
    }
}
