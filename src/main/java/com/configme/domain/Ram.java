package com.configme.domain;

import com.configme.domain.enumeration.RamType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ram.
 * Filters: Frequency, Quantity, Capacity
 */
@Entity
@Table(name = "ram")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ram extends Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RamType type;

    @NotNull
    @Column(name = "frequency", nullable = false)
    private Float frequency;

    @NotNull
    @Column(name = "unit_size", nullable = false)
    private Integer unitSize;

    @NotNull
    @Min(value = 2)
    @Max(value = 8)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "cas", nullable = false)
    private String cas;

    public RamType getType() {
        return this.type;
    }

    public Ram type(RamType type) {
        this.type = type;
        return this;
    }

    public void setType(RamType type) {
        this.type = type;
    }

    public Float getFrequency() {
        return this.frequency;
    }

    public Ram frequency(Float frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Float frequency) {
        this.frequency = frequency;
    }

    public Integer getUnitSize() {
        return this.unitSize;
    }

    public Ram unitSize(Integer unitSize) {
        this.unitSize = unitSize;
        return this;
    }

    public void setUnitSize(Integer unitSize) {
        this.unitSize = unitSize;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Ram quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCas() {
        return this.cas;
    }

    public Ram cas(String cas) {
        this.cas = cas;
        return this;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ram)) {
            return false;
        }
        return super.getId() != null && super.getId().equals(((Ram) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ram{" +
            "id=" + super.getId() +
            ", type='" + getType() + "'" +
            ", frequency=" + getFrequency() +
            ", unitSize=" + getUnitSize() +
            ", quantity=" + getQuantity() +
            ", cas='" + getCas() + "'" +
            "}";
    }
}
