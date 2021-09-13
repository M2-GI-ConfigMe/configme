package com.configme.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ventirad.
 * Filters: Brand, HasThermalPaste
 */
@Entity
@Table(name = "ventirad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ventirad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "range_fan_speed", nullable = false)
    private String rangeFanSpeed;

    @Column(name = "noise")
    private Integer noise;

    @NotNull
    @Column(name = "has_thermal_paste", nullable = false)
    private Boolean hasThermalPaste;

    @Embedded
    private Dimension dimension;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ventirad id(Long id) {
        this.id = id;
        return this;
    }

    public String getRangeFanSpeed() {
        return this.rangeFanSpeed;
    }

    public Ventirad rangeFanSpeed(String rangeFanSpeed) {
        this.rangeFanSpeed = rangeFanSpeed;
        return this;
    }

    public void setRangeFanSpeed(String rangeFanSpeed) {
        this.rangeFanSpeed = rangeFanSpeed;
    }

    public Integer getNoise() {
        return this.noise;
    }

    public Ventirad noise(Integer noise) {
        this.noise = noise;
        return this;
    }

    public void setNoise(Integer noise) {
        this.noise = noise;
    }

    public Boolean getHasThermalPaste() {
        return this.hasThermalPaste;
    }

    public Ventirad hasThermalPaste(Boolean hasThermalPaste) {
        this.hasThermalPaste = hasThermalPaste;
        return this;
    }

    public void setHasThermalPaste(Boolean hasThermalPaste) {
        this.hasThermalPaste = hasThermalPaste;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public Ventirad dimension(Dimension dimension) {
        this.setDimension(dimension);
        return this;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ventirad)) {
            return false;
        }
        return id != null && id.equals(((Ventirad) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ventirad{" +
            "id=" + getId() +
            ", rangeFanSpeed='" + getRangeFanSpeed() + "'" +
            ", noise=" + getNoise() +
            ", hasThermalPaste='" + getHasThermalPaste() + "'" +
            "}";
    }
}
