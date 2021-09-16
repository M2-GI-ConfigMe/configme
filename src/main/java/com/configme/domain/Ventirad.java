package com.configme.domain;

import com.configme.domain.enumeration.SocketType;
import java.io.Serializable;
import java.util.Collection;
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
public class Ventirad extends Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @ElementCollection(targetClass = SocketType.class)
    @CollectionTable(name = "ventirad_sockets", joinColumns = @JoinColumn(name = "ventirad_id", referencedColumnName = "id"))
    @Enumerated(EnumType.STRING)
    private Collection<SocketType> sockets;

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
        return super.getId() != null && super.getId().equals(((Ventirad) o).getId());
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
            "id=" + super.getId() +
            ", rangeFanSpeed='" + getRangeFanSpeed() + "'" +
            ", noise=" + getNoise() +
            ", hasThermalPaste='" + getHasThermalPaste() + "'" +
            ", sockets='" + getSockets() + "'" +
            "}";
    }

    public Collection<SocketType> getSockets() {
        return sockets;
    }

    public void setSockets(Collection<SocketType> sockets) {
        this.sockets = sockets;
    }

    public void sockets(Collection<SocketType> sockets) {
        this.sockets = sockets;
    }
}
