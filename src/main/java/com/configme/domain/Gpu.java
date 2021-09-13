package com.configme.domain;

import com.configme.domain.enumeration.BusType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Gpu.
 * Filters: Memory, Brand
 */
@Entity
@Table(name = "gpu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Gpu extends Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "frequency", nullable = false)
    private Float frequency;

    @NotNull
    @Column(name = "memory", nullable = false)
    private Integer memory;

    @NotNull
    @Column(name = "consumption", nullable = false)
    private Integer consumption;

    @NotNull
    @Column(name = "clock_speed", nullable = false)
    private Integer clockSpeed;

    @NotNull
    @Column(name = "lithography", nullable = false)
    private Integer lithography;

    @NotNull
    @Column(name = "output", nullable = false)
    private String output;

    @NotNull
    @Column(name = "input_power", nullable = false)
    private String inputPower;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bus", nullable = false)
    private BusType bus;

    @Embedded
    private Dimension dimension;

    public Float getFrequency() {
        return this.frequency;
    }

    public Gpu frequency(Float frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Float frequency) {
        this.frequency = frequency;
    }

    public Integer getMemory() {
        return this.memory;
    }

    public Gpu memory(Integer memory) {
        this.memory = memory;
        return this;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getConsumption() {
        return this.consumption;
    }

    public Gpu consumption(Integer consumption) {
        this.consumption = consumption;
        return this;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }

    public Integer getClockSpeed() {
        return this.clockSpeed;
    }

    public Gpu clockSpeed(Integer clockSpeed) {
        this.clockSpeed = clockSpeed;
        return this;
    }

    public void setClockSpeed(Integer clockSpeed) {
        this.clockSpeed = clockSpeed;
    }

    public Integer getLithography() {
        return this.lithography;
    }

    public Gpu lithography(Integer lithography) {
        this.lithography = lithography;
        return this;
    }

    public void setLithography(Integer lithography) {
        this.lithography = lithography;
    }

    public String getOutput() {
        return this.output;
    }

    public Gpu output(String output) {
        this.output = output;
        return this;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getInputPower() {
        return this.inputPower;
    }

    public Gpu inputPower(String inputPower) {
        this.inputPower = inputPower;
        return this;
    }

    public void setInputPower(String inputPower) {
        this.inputPower = inputPower;
    }

    public BusType getBus() {
        return this.bus;
    }

    public Gpu bus(BusType bus) {
        this.bus = bus;
        return this;
    }

    public void setBus(BusType bus) {
        this.bus = bus;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public Gpu dimension(Dimension dimension) {
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
        if (!(o instanceof Gpu)) {
            return false;
        }
        return super.getId() != null && super.getId().equals(((Gpu) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gpu{" +
            "id=" + super.getId() +
            ", frequency=" + getFrequency() +
            ", memory=" + getMemory() +
            ", consumption=" + getConsumption() +
            ", clockSpeed=" + getClockSpeed() +
            ", lithography=" + getLithography() +
            ", output='" + getOutput() + "'" +
            ", inputPower='" + getInputPower() + "'" +
            ", bus='" + getBus() + "'" +
            "}";
    }
}
