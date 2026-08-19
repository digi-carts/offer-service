package com.digicart.offer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

/**
 * Request/response DTO: Offer Request.
 */
public class OfferRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String type;

    private String scope = "PRODUCT";

    private Double value = 0.0;

    private Integer maxUses;

    private Instant expiresAt;

    private String refCode;

    private String description;

    private Double minOrderAmt = 0.0;

    private String storeId;

    private Boolean active = true;

    /**
     * Returns code.
     * @return the string
     */
    public String getCode() { return code; }
    /**
     * Sets code.
     *
     * @param code offer or coupon code
     */
    public void setCode(String code) { this.code = code; }
    /**
     * Returns type.
     * @return the string
     */
    public String getType() { return type; }
    /**
     * Sets type.
     *
     * @param type type
     */
    public void setType(String type) { this.type = type; }
    /**
     * Returns scope.
     * @return the string
     */
    public String getScope() { return scope; }
    /**
     * Sets scope.
     *
     * @param scope scope
     */
    public void setScope(String scope) { this.scope = scope; }
    /**
     * Returns value.
     * @return the double
     */
    public Double getValue() { return value; }
    /**
     * Sets value.
     *
     * @param value value
     */
    public void setValue(Double value) { this.value = value; }
    /**
     * Returns max uses.
     * @return the integer
     */
    public Integer getMaxUses() { return maxUses; }
    /**
     * Sets max uses.
     *
     * @param maxUses max uses
     */
    public void setMaxUses(Integer maxUses) { this.maxUses = maxUses; }
    /**
     * Returns expires at.
     * @return the instant
     */
    public Instant getExpiresAt() { return expiresAt; }
    /**
     * Sets expires at.
     *
     * @param expiresAt expires at
     */
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
    /**
     * Returns ref code.
     * @return the string
     */
    public String getRefCode() { return refCode; }
    /**
     * Sets ref code.
     *
     * @param refCode ref code
     */
    public void setRefCode(String refCode) { this.refCode = refCode; }
    /**
     * Returns description.
     * @return the string
     */
    public String getDescription() { return description; }
    /**
     * Sets description.
     *
     * @param description description
     */
    public void setDescription(String description) { this.description = description; }
    /**
     * Returns min order amt.
     * @return the double
     */
    public Double getMinOrderAmt() { return minOrderAmt; }
    /**
     * Sets min order amt.
     *
     * @param minOrderAmt min order amt
     */
    public void setMinOrderAmt(Double minOrderAmt) { this.minOrderAmt = minOrderAmt; }
    /**
     * Returns store id.
     * @return the string
     */
    public String getStoreId() { return storeId; }
    /**
     * Sets store id.
     *
     * @param storeId store (tenant) identifier
     */
    public void setStoreId(String storeId) { this.storeId = storeId; }
    /**
     * Returns active.
     * @return the boolean
     */
    public Boolean getActive() { return active; }
    /**
     * Sets active.
     *
     * @param active active
     */
    public void setActive(Boolean active) { this.active = active; }
}
