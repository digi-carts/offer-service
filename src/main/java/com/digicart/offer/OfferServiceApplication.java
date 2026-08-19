package com.digicart.offer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Spring Boot entry point for the <em>offer-service</em> microservice.
 */
@SpringBootApplication
@EnableJpaAuditing
public class OfferServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OfferServiceApplication.class, args);
    }
}
