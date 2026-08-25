package com.digicart.offer.repository;

import com.digicart.offer.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for offer  persistence.
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
    List<Offer> findByStoreId(String storeId);
    Optional<Offer> findByCode(String code);
    List<Offer> findByActiveTrue();
    List<Offer> findByStoreIdAndActiveTrue(String storeId);
}
