package com.digicart.offer.service;

import com.digicart.offer.dto.OfferRequest;
import com.digicart.offer.entity.Offer;
import com.digicart.offer.repository.OfferRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Application service implementing offer use cases for <em>offer-service</em>.
 */
@Service
public class OfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Map<String, Object> validate(String code, String scope) {
        Map<String, Object> result = new HashMap<>();
        try {
            Offer offer = findByCode(code);
            if (!Boolean.TRUE.equals(offer.getActive())) {
                result.put("valid", false); result.put("discount", 0); result.put("offerId", "");
                result.put("description", offer.getDescription()); result.put("reason", "Offer is inactive");
                return result;
            }
            if (offer.getExpiresAt() != null && offer.getExpiresAt().isBefore(Instant.now())) {
                result.put("valid", false); result.put("discount", 0); result.put("offerId", "");
                result.put("description", offer.getDescription()); result.put("reason", "Offer has expired");
                return result;
            }
            if (offer.getMaxUses() != null && offer.getUsedCount() >= offer.getMaxUses()) {
                result.put("valid", false); result.put("discount", 0); result.put("offerId", "");
                result.put("description", offer.getDescription()); result.put("reason", "Usage limit reached");
                return result;
            }
            result.put("valid", true); result.put("discount", offer.getValue());
            result.put("offerId", offer.getId()); result.put("description", offer.getDescription());
        } catch (EntityNotFoundException e) {
            result.put("valid", false); result.put("discount", 0); result.put("offerId", "");
            result.put("description", null); result.put("reason", "Code not found");
        }
        return result;
    }

    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    public Offer findById(UUID id) {
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

    public Offer update(UUID id, OfferRequest request) {
        Offer offer = findById(id);
        mapRequestToOffer(request, offer);
        return offerRepository.save(offer);
    }

    public Offer patch(UUID id, Map<String, Object> updates) {
        Offer offer = findById(id);
        if (updates.containsKey("active")) offer.setActive((Boolean) updates.get("active"));
        if (updates.containsKey("code")) offer.setCode((String) updates.get("code"));
        if (updates.containsKey("type")) offer.setType((String) updates.get("type"));
        if (updates.containsKey("scope")) offer.setScope((String) updates.get("scope"));
        if (updates.containsKey("value")) offer.setValue(((Number) updates.get("value")).doubleValue());
        if (updates.containsKey("maxUses")) offer.setMaxUses(updates.get("maxUses") != null ? ((Number) updates.get("maxUses")).intValue() : null);
        if (updates.containsKey("expiresAt")) offer.setExpiresAt(updates.get("expiresAt") != null ? Instant.parse((String) updates.get("expiresAt")) : null);
        if (updates.containsKey("description")) offer.setDescription((String) updates.get("description"));
        if (updates.containsKey("minOrderAmt")) offer.setMinOrderAmt(((Number) updates.get("minOrderAmt")).doubleValue());
        return offerRepository.save(offer);
    }

    public void delete(UUID id) {
        Offer offer = findById(id);
        offerRepository.delete(offer);
    }

    public List<Offer> findPublicOffers() {
        return offerRepository.findByType("PUBLIC");
    }

    public Map<String, Object> applyOffer(String code, String orderId) {
        Map<String, Object> result = new HashMap<>();
        try {
            Offer offer = findByCode(code);
            offer.setUsedCount(offer.getUsedCount() + 1);
            offerRepository.save(offer);
            result.put("applied", true);
            result.put("code", code);
            result.put("discount", offer.getValue());
        } catch (EntityNotFoundException e) {
            result.put("applied", true);
            result.put("code", code);
            result.put("discount", 0);
        }
        return result;
    }

    public Offer incrementUsedCount(UUID id) {
        Offer offer = findById(id);
        offer.setUsedCount(offer.getUsedCount() + 1);
        return offerRepository.save(offer);
    }

    public java.util.Optional<Offer> findReferralByStoreId(String storeId) {
        return offerRepository.findByStoreIdAndType(storeId, "REFERRAL");
    }

    @org.springframework.transaction.annotation.Transactional
    public Offer createReferralCode(String storeId) {
        return offerRepository.findByStoreIdAndType(storeId, "REFERRAL").orElseGet(() -> {
            String suffix = storeId.substring(Math.max(0, storeId.length() - 6)).toUpperCase();
            Offer offer = new Offer();
            offer.setCode("REF-" + suffix);
            offer.setType("REFERRAL");
            offer.setScope("ORDER");
            offer.setValue(0.0);
            offer.setStoreId(storeId);
            offer.setActive(true);
            return offerRepository.save(offer);
        });
    }

    @org.springframework.transaction.annotation.Transactional
    public java.util.Map<String, Object> applyReferral(String referralCode, String applyingStoreId) {
        Offer referral;
        try {
            referral = findByCode(referralCode);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return java.util.Map.of("success", false, "message", "Invalid referral code");
        }
        if (!"REFERRAL".equals(referral.getType())) {
            return java.util.Map.of("success", false, "message", "Invalid referral code");
        }
        String couponCode = "REFERME-" + applyingStoreId.substring(Math.max(0, applyingStoreId.length() - 6)).toUpperCase();
        Offer coupon = offerRepository.findByCode(couponCode).orElseGet(() -> {
            Offer o = new Offer();
            o.setCode(couponCode);
            o.setType("COUPON");
            o.setScope("ORDER");
            o.setValue(referral.getValue());
            o.setStoreId(applyingStoreId);
            o.setActive(true);
            o.setRefCode(referralCode);
            return o;
        });
        offerRepository.save(coupon);
        return java.util.Map.of("success", true, "couponCode", couponCode);
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
