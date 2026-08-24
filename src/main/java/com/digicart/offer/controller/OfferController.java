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
            @PathVariable String id,
            @RequestBody Map<String, Object> updates,
            @RequestHeader(value = "X-Store-Id", required = false) String storeId) {
        return ResponseEntity.ok(offerService.patch(id, updates));
    }

    @DeleteMapping("/store/{id}")
    public ResponseEntity<Void> deleteForStore(
            @PathVariable String id,
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
            @PathVariable String id,
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
            @PathVariable String id,
            @Valid @RequestBody OfferRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(offerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        offerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/use")
    public ResponseEntity<Offer> incrementUsage(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(offerService.incrementUsedCount(id));
    }
}
