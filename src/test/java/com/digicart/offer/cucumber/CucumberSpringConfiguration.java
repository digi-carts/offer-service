package com.digicart.offer.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import com.digicart.offer.exception.GlobalExceptionHandler;
import com.digicart.offer.controller.HealthController;
import com.digicart.offer.controller.OfferController;
import com.digicart.offer.service.OfferService;

@CucumberContextConfiguration
@WebMvcTest(controllers = { HealthController.class, OfferController.class })
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
public class CucumberSpringConfiguration {
    @MockBean
    OfferService offerService;

}
