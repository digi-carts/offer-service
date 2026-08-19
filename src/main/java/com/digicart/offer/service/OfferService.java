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

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    public Offer findById(String id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with id: " + id));
    }

    public Offer findByCode(String code) {
        return offerRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with code: " + code));
    }

    public List<Offer> findByStoreId(String storeId) {
        return offerRepository.findByStoreId(storeId);
    }

    public List<Offer> findActiveOffers() {
        return offerRepository.findByActiveTrue();
    }

    public List<Offer> findActiveOffersByStoreId(String storeId) {
        return offerRepository.findByStoreIdAndActiveTrue(storeId);
    }

    public Offer create(OfferRequest request) {
        Offer offer = new Offer();
        mapRequestToOffer(request, offer);
        return offerRepository.save(offer);
    }

    public Offer update(String id, OfferRequest request) {
        Offer offer = findById(id);
        mapRequestToOffer(request, offer);
        return offerRepository.save(offer);
    }

    public void delete(String id) {
        Offer offer = findById(id);
        offerRepository.delete(offer);
    }

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
