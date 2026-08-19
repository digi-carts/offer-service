package com.digicart.offer.service;

import com.digicart.offer.dto.OfferRequest;
import com.digicart.offer.entity.Offer;
import com.digicart.offer.repository.OfferRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service implementing offer use cases for <em>offer-service</em>.
 */
@Service
public class OfferService {

    private final OfferRepository offerRepository;

    /**
     * Creates a new {@code OfferService}.
     *
     * @param offerRepository offer repository collaborator
     */
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    /**
     * Finds all.
     * @return matching records
     */
    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    /**
     * Finds by id.
     *
     * @param id resource identifier
     * @return the offer
     */
    public Offer findById(String id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with id: " + id));
    }

    /**
     * Finds by code.
     *
     * @param code offer or coupon code
     * @return the offer
     */
    public Offer findByCode(String code) {
        return offerRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with code: " + code));
    }

    /**
     * Finds by store id.
     *
     * @param storeId store (tenant) identifier
     * @return matching records
     */
    public List<Offer> findByStoreId(String storeId) {
        return offerRepository.findByStoreId(storeId);
    }

    /**
     * Finds active offers.
     * @return matching records
     */
    public List<Offer> findActiveOffers() {
        return offerRepository.findByActiveTrue();
    }

    /**
     * Finds active offers by store id.
     *
     * @param storeId store (tenant) identifier
     * @return matching records
     */
    public List<Offer> findActiveOffersByStoreId(String storeId) {
        return offerRepository.findByStoreIdAndActiveTrue(storeId);
    }

    /**
     * Creates a new record.
     *
     * @param request request payload
     * @return the offer
     */
    public Offer create(OfferRequest request) {
        Offer offer = new Offer();
        mapRequestToOffer(request, offer);
        return offerRepository.save(offer);
    }

    /**
     * Updates an existing record.
     *
     * @param id resource identifier
     * @param request request payload
     * @return the offer
     */
    public Offer update(String id, OfferRequest request) {
        Offer offer = findById(id);
        mapRequestToOffer(request, offer);
        return offerRepository.save(offer);
    }

    /**
     * Deletes the record.
     *
     * @param id resource identifier
     */
    public void delete(String id) {
        Offer offer = findById(id);
        offerRepository.delete(offer);
    }

    /**
     * Increment used count.
     *
     * @param id resource identifier
     * @return the offer
     */
    public Offer incrementUsedCount(String id) {
        Offer offer = findById(id);
        offer.setUsedCount(offer.getUsedCount() + 1);
        return offerRepository.save(offer);
    }

    private void mapRequestToOffer(OfferRequest request, Offer offer) {
        if (request.getCode() != null) offer.setCode(request.getCode());
        if (request.getType() != null) offer.setType(request.getType());
        if (request.getScope() != null) offer.setScope(request.getScope());
        if (request.getValue() != null) offer.setValue(request.getValue());
        if (request.getMaxUses() != null) offer.setMaxUses(request.getMaxUses());
        if (request.getExpiresAt() != null) offer.setExpiresAt(request.getExpiresAt());
        if (request.getRefCode() != null) offer.setRefCode(request.getRefCode());
        if (request.getDescription() != null) offer.setDescription(request.getDescription());
        if (request.getMinOrderAmt() != null) offer.setMinOrderAmt(request.getMinOrderAmt());
        if (request.getStoreId() != null) offer.setStoreId(request.getStoreId());
        if (request.getActive() != null) offer.setActive(request.getActive());
    }
}
