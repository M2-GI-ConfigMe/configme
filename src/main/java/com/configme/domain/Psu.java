package com.configme.domain;

import com.configme.domain.enumeration.ModularityType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Psu.
 * Filters: ModularityType, Brand, Power
 */
@Entity
@Table(name = "psu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Psu extends Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "power", nullable = false)
    private Integer power;

    @Column(name = "certification")
    private String certification;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "modularity", nullable = false)
    private ModularityType modularity;

    @NotNull
    @Min(value = 1)
    @Column(name = "nb_sata", nullable = false)
    private Integer nbSata;

    @NotNull
    @Min(value = 1)
    @Column(name = "nb_pci_e", nullable = false)
    private Integer nbPciE;

    @NotNull
    @Column(name = "outputs", nullable = false)
    private String outputs;

    @Embedded
    private Dimension dimension;

    public Integer getPower() {
        return this.power;
    }

    public Psu power(Integer power) {
        this.power = power;
        return this;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getCertification() {
        return this.certification;
    }

    public Psu certification(String certification) {
        this.certification = certification;
        return this;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public ModularityType getModularity() {
        return this.modularity;
    }

    public Psu modularity(ModularityType modularity) {
        this.modularity = modularity;
        return this;
    }

    public void setModularity(ModularityType modularity) {
        this.modularity = modularity;
    }

    public Integer getNbSata() {
        return this.nbSata;
    }

    public Psu nbSata(Integer nbSata) {
        this.nbSata = nbSata;
        return this;
    }

    public void setNbSata(Integer nbSata) {
        this.nbSata = nbSata;
    }

    public Integer getNbPciE() {
        return this.nbPciE;
    }

    public Psu nbPciE(Integer nbPciE) {
        this.nbPciE = nbPciE;
        return this;
    }

    public void setNbPciE(Integer nbPciE) {
        this.nbPciE = nbPciE;
    }

    public String getOutputs() {
        return this.outputs;
    }

    public Psu outputs(String outputs) {
        this.outputs = outputs;
        return this;
    }

    public void setOutputs(String outputs) {
        this.outputs = outputs;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public Psu dimension(Dimension dimension) {
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
        if (!(o instanceof Psu)) {
            return false;
        }
        return super.getId() != null && super.getId().equals(((Psu) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Psu{" +
            "id=" + super.getId() +
            ", power=" + getPower() +
            ", certification='" + getCertification() + "'" +
            ", modularity='" + getModularity() + "'" +
            ", nbSata=" + getNbSata() +
            ", nbPciE=" + getNbPciE() +
            ", outputs='" + getOutputs() + "'" +
            ", dimension='" + getDimension() + "'" +
            "}";
    }
}
