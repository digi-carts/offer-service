package com.digicart.offer.repository;

import com.digicart.offer.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for offer  persistence.
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {
    /**
     * Finds by store id.
     *
     * @param storeId store (tenant) identifier
     * @return matching records
     */
    List<Offer> findByStoreId(String storeId);
    /**
     * Finds by code.
     *
     * @param code offer or coupon code
     * @return the value if present
     */
    Optional<Offer> findByCode(String code);
    /**
     * Finds by active true.
     * @return matching records
     */
    List<Offer> findByActiveTrue();
    /**
     * Finds by store id and active true.
     *
     * @param storeId store (tenant) identifier
     * @return matching records
     */
    List<Offer> findByStoreIdAndActiveTrue(String storeId);
}
