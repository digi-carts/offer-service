package com.digicart.offer.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * JPA entity mapped in this service schema (Offer).
 */
@Entity
@Table(name = "offers", schema = "offer_svc")
@EntityListeners(AuditingEntityListener.class)
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

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

    /**
     * Creates a new {@code Offer}.
     */
    public Offer() {}
    /**
     * Returns id.
     * @return the string
     */
    public String getId() { return id; }
    /**
     * Sets id.
     *
     * @param id resource identifier
     */
    public void setId(String id) { this.id = id; }
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
     * Returns used count.
     * @return the integer
     */
    public Integer getUsedCount() { return usedCount; }
    /**
     * Sets used count.
     *
     * @param usedCount used count
     */
    public void setUsedCount(Integer usedCount) { this.usedCount = usedCount; }
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
    /**
     * Returns created at.
     * @return the instant
     */
    public Instant getCreatedAt() { return createdAt; }
    /**
     * Sets created at.
     *
     * @param createdAt created at
     */
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    /**
     * Returns updated at.
     * @return the instant
     */
    public Instant getUpdatedAt() { return updatedAt; }
    /**
     * Sets updated at.
     *
     * @param updatedAt updated at
     */
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
