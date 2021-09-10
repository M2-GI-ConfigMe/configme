package com.configme.domain;

import com.configme.domain.enumeration.MemoryType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HardDrive.
 */
@Entity
@Table(name = "hard_drive")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HardDrive implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @Column(name = "speed_write", nullable = false)
    private Float speedWrite;

    @NotNull
    @Column(name = "speed_read", nullable = false)
    private Float speedRead;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MemoryType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HardDrive id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public HardDrive capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Float getSpeedWrite() {
        return this.speedWrite;
    }

    public HardDrive speedWrite(Float speedWrite) {
        this.speedWrite = speedWrite;
        return this;
    }

    public void setSpeedWrite(Float speedWrite) {
        this.speedWrite = speedWrite;
    }

    public Float getSpeedRead() {
        return this.speedRead;
    }

    public HardDrive speedRead(Float speedRead) {
        this.speedRead = speedRead;
        return this;
    }

    public void setSpeedRead(Float speedRead) {
        this.speedRead = speedRead;
    }

    public MemoryType getType() {
        return this.type;
    }

    public HardDrive type(MemoryType type) {
        this.type = type;
        return this;
    }

    public void setType(MemoryType type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HardDrive)) {
            return false;
        }
        return id != null && id.equals(((HardDrive) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HardDrive{" +
            "id=" + getId() +
            ", capacity=" + getCapacity() +
            ", speedWrite=" + getSpeedWrite() +
            ", speedRead=" + getSpeedRead() +
            ", type='" + getType() + "'" +
            "}";
    }
}
