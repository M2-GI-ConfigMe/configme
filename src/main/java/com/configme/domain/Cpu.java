package com.configme.domain;

import com.configme.domain.enumeration.SocketType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cpu.
 * Filters: Socket, Brand, HasVentirad, HasGpu
 */
@Entity
@Table(name = "cpu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cpu extends Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "frequency", nullable = false)
    private Float frequency;

    @Column(name = "cache_l_1")
    private Integer cacheL1;

    @Column(name = "cache_l_2")
    private Integer cacheL2;

    @Column(name = "cache_l_3")
    private Integer cacheL3;

    @NotNull
    @Min(value = 1)
    @Column(name = "nb_heart", nullable = false)
    private Integer nbHeart;

    @NotNull
    @Min(value = 1)
    @Column(name = "nb_thread", nullable = false)
    private Integer nbThread;

    @NotNull
    @Column(name = "has_ventirad", nullable = false)
    private Boolean hasVentirad;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "socket_type", nullable = false)
    private SocketType socketType;

    @NotNull
    @Column(name = "lithography", nullable = false)
    private Integer lithography;

    @NotNull
    @Column(name = "ram_frequency_max", nullable = false)
    private Float ramFrequencyMax;

    @NotNull
    @Column(name = "consumption", nullable = false)
    private Integer consumption;

    @NotNull
    @Column(name = "has_gpu", nullable = false)
    private Boolean hasGpu;

    public Float getFrequency() {
        return this.frequency;
    }

    public Cpu frequency(Float frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Float frequency) {
        this.frequency = frequency;
    }

    public Integer getCacheL1() {
        return this.cacheL1;
    }

    public Cpu cacheL1(Integer cacheL1) {
        this.cacheL1 = cacheL1;
        return this;
    }

    public void setCacheL1(Integer cacheL1) {
        this.cacheL1 = cacheL1;
    }

    public Integer getCacheL2() {
        return this.cacheL2;
    }

    public Cpu cacheL2(Integer cacheL2) {
        this.cacheL2 = cacheL2;
        return this;
    }

    public void setCacheL2(Integer cacheL2) {
        this.cacheL2 = cacheL2;
    }

    public Integer getCacheL3() {
        return this.cacheL3;
    }

    public Cpu cacheL3(Integer cacheL3) {
        this.cacheL3 = cacheL3;
        return this;
    }

    public void setCacheL3(Integer cacheL3) {
        this.cacheL3 = cacheL3;
    }

    public Integer getNbHeart() {
        return this.nbHeart;
    }

    public Cpu nbHeart(Integer nbHeart) {
        this.nbHeart = nbHeart;
        return this;
    }

    public void setNbHeart(Integer nbHeart) {
        this.nbHeart = nbHeart;
    }

    public Integer getNbThread() {
        return this.nbThread;
    }

    public Cpu nbThread(Integer nbThread) {
        this.nbThread = nbThread;
        return this;
    }

    public void setNbThread(Integer nbThread) {
        this.nbThread = nbThread;
    }

    public Boolean getHasVentirad() {
        return this.hasVentirad;
    }

    public Cpu hasVentirad(Boolean hasVentirad) {
        this.hasVentirad = hasVentirad;
        return this;
    }

    public void setHasVentirad(Boolean hasVentirad) {
        this.hasVentirad = hasVentirad;
    }

    public SocketType getSocketType() {
        return this.socketType;
    }

    public Cpu socketType(SocketType socketType) {
        this.socketType = socketType;
        return this;
    }

    public void setSocketType(SocketType socketType) {
        this.socketType = socketType;
    }

    public Integer getLithography() {
        return this.lithography;
    }

    public Cpu lithography(Integer lithography) {
        this.lithography = lithography;
        return this;
    }

    public void setLithography(Integer lithography) {
        this.lithography = lithography;
    }

    public Float getRamFrequencyMax() {
        return this.ramFrequencyMax;
    }

    public Cpu ramFrequencyMax(Float ramFrequencyMax) {
        this.ramFrequencyMax = ramFrequencyMax;
        return this;
    }

    public void setRamFrequencyMax(Float ramFrequencyMax) {
        this.ramFrequencyMax = ramFrequencyMax;
    }

    public Integer getConsumption() {
        return this.consumption;
    }

    public Cpu consumption(Integer consumption) {
        this.consumption = consumption;
        return this;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }

    public Boolean getHasGpu() {
        return this.hasGpu;
    }

    public Cpu hasGpu(Boolean hasGpu) {
        this.hasGpu = hasGpu;
        return this;
    }

    public void setHasGpu(Boolean hasGpu) {
        this.hasGpu = hasGpu;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cpu)) {
            return false;
        }
        return super.getId() != null && super.getId().equals(((Cpu) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cpu{" +
            "id=" + super.getId() +
            ", frequency=" + getFrequency() +
            ", cacheL1=" + getCacheL1() +
            ", cacheL2=" + getCacheL2() +
            ", cacheL3=" + getCacheL3() +
            ", nbHeart=" + getNbHeart() +
            ", nbThread=" + getNbThread() +
            ", hasVentirad='" + getHasVentirad() + "'" +
            ", socketType='" + getSocketType() + "'" +
            ", lithography=" + getLithography() +
            ", ramFrequencyMax=" + getRamFrequencyMax() +
            ", consumption=" + getConsumption() +
            ", hasGpu='" + getHasGpu() + "'" +
            "}";
    }
}
