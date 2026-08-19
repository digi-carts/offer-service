package com.digicart.offer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

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

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public Integer getMaxUses() { return maxUses; }
    public void setMaxUses(Integer maxUses) { this.maxUses = maxUses; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public String getRefCode() { return refCode; }
    public void setRefCode(String refCode) { this.refCode = refCode; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getMinOrderAmt() { return minOrderAmt; }
    public void setMinOrderAmt(Double minOrderAmt) { this.minOrderAmt = minOrderAmt; }

    public String getStoreId() { return storeId; }
    public void setStoreId(String storeId) { this.storeId = storeId; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
