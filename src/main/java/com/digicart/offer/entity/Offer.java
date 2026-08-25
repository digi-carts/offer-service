package com.digicart.offer.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

/**
 * JPA entity mapped in this service schema (Offer).
 */
@Entity
@Table(name = "offers", schema = "offer_svc")
@EntityListeners(AuditingEntityListener.class)
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "scope", nullable = false)
    private String scope = "PRODUCT";

    @Column(name = "value", nullable = false)
    private Double value = 0.0;

    @Column(name = "max_uses")
    private Integer maxUses;

    @Column(name = "used_count", nullable = false)
    private Integer usedCount = 0;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "ref_code")
    private String refCode;

    @Column(name = "description")
    private String description;

    @Column(name = "min_order_amt", nullable = false)
    private Double minOrderAmt = 0.0;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Offer() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

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

    public Integer getUsedCount() { return usedCount; }
    public void setUsedCount(Integer usedCount) { this.usedCount = usedCount; }

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

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
