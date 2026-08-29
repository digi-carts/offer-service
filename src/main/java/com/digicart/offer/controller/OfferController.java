package com.digicart.offer.controller;

import com.digicart.offer.dto.OfferRequest;
import com.digicart.offer.entity.Offer;
import com.digicart.offer.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * REST controller exposing offer HTTP APIs for <em>offer-service</em>.
 */
@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/public")
    public ResponseEntity<List<Offer>> getPublic() {
        return ResponseEntity.ok(offerService.findPublicOffers());
    }

    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> apply(
            @RequestBody Map<String, String> body,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        return ResponseEntity.ok(offerService.applyOffer(body.get("code"), body.get("orderId")));
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validate(
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(offerService.validate(body.get("code"), body.get("scope")));
    }

    @GetMapping("/store")
    public ResponseEntity<Map<String, Object>> getByStore(
            @RequestHeader(value = "X-Store-Id") String storeId) {
        return ResponseEntity.ok(Map.of("offers", offerService.findByStoreId(storeId)));
    }

    @PostMapping("/store")
    public ResponseEntity<Offer> createForStore(
            @Valid @RequestBody OfferRequest request,
            @RequestHeader(value = "X-Store-Id") String storeId) {
        request.setStoreId(storeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(offerService.create(request));
    }

    @PatchMapping("/store/{id}")
    public ResponseEntity<Offer> patchForStore(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates,
            @RequestHeader(value = "X-Store-Id", required = false) String storeId) {
        return ResponseEntity.ok(offerService.patch(id, updates));
    }

    @DeleteMapping("/store/{id}")
    public ResponseEntity<Void> deleteForStore(
            @PathVariable UUID id,
            @RequestHeader(value = "X-Store-Id", required = false) String storeId) {
        offerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Offer>> getAll(
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole,
            @RequestParam(required = false) String storeId,
            @RequestParam(required = false) Boolean active) {
        if (storeId != null && Boolean.TRUE.equals(active)) {
            return ResponseEntity.ok(offerService.findActiveOffersByStoreId(storeId));
        } else if (storeId != null) {
            return ResponseEntity.ok(offerService.findByStoreId(storeId));
        } else if (Boolean.TRUE.equals(active)) {
            return ResponseEntity.ok(offerService.findActiveOffers());
        }
        return ResponseEntity.ok(offerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getById(
            @PathVariable UUID id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(offerService.findById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Offer> getByCode(
            @PathVariable String code,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(offerService.findByCode(code));
    }

    @PostMapping
    public ResponseEntity<Offer> create(
            @Valid @RequestBody OfferRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(offerService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offer> update(
            @PathVariable UUID id,
            @Valid @RequestBody OfferRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(offerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        offerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/use")
    public ResponseEntity<Offer> incrementUsage(
            @PathVariable UUID id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(offerService.incrementUsedCount(id));
    }

    @GetMapping("/store/referral")
    public ResponseEntity<Map<String, Object>> getStoreReferral(
            @RequestHeader("X-Store-Id") String storeId) {
        return offerService.findReferralByStoreId(storeId)
                .map(o -> ResponseEntity.ok(Map.<String, Object>of("offer", o)))
                .orElse(ResponseEntity.ok(Map.of("offer", (Object) null)));
    }

    @PostMapping("/store/referral")
    public ResponseEntity<Offer> createStoreReferral(
            @RequestHeader("X-Store-Id") String storeId) {
        return ResponseEntity.ok(offerService.createReferralCode(storeId));
    }

    @PostMapping("/referral/apply")
    public ResponseEntity<Map<String, Object>> applyReferral(
            @RequestBody Map<String, String> body,
            @RequestHeader(value = "X-Store-Id", required = false) String storeId) {
        String applyingStore = storeId != null ? storeId : body.get("storeId");
        return ResponseEntity.ok(offerService.applyReferral(body.get("code"), applyingStore));
    }
}
