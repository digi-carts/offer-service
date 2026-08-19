package com.digicart.offer.controller;

import com.digicart.offer.dto.OfferRequest;
import com.digicart.offer.entity.Offer;
import com.digicart.offer.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing offer HTTP APIs for <em>offer-service</em>.
 */
@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    /**
     * Creates a new {@code OfferController}.
     *
     * @param offerService offer service collaborator
     */
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    /**
     * Handles GET.
     *
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @param storeId store (tenant) identifier
     * @param active active
     * @return HTTP response
     */
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

    /**
     * Handles {@code GET /{id}}.
     *
     * @param id resource identifier
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @GetMapping("/{id}")
    public ResponseEntity<Offer> getById(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(offerService.findById(id));
    }

    /**
     * Handles {@code GET /code/{code}}.
     *
     * @param code offer or coupon code
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<Offer> getByCode(
            @PathVariable String code,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(offerService.findByCode(code));
    }

    /**
     * Handles POST.
     *
     * @param request request payload
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @PostMapping
    public ResponseEntity<Offer> create(
            @Valid @RequestBody OfferRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(offerService.create(request));
    }

    /**
     * Handles {@code PUT /{id}}.
     *
     * @param id resource identifier
     * @param request request payload
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @PutMapping("/{id}")
    public ResponseEntity<Offer> update(
            @PathVariable String id,
            @Valid @RequestBody OfferRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(offerService.update(id, request));
    }

    /**
     * Handles {@code DELETE /{id}}.
     *
     * @param id resource identifier
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        offerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Handles {@code POST /{id}/use}.
     *
     * @param id resource identifier
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @PostMapping("/{id}/use")
    public ResponseEntity<Offer> incrementUsage(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.ok(offerService.incrementUsedCount(id));
    }
}
