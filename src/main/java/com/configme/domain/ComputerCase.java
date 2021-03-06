package com.configme.domain;

import com.configme.domain.enumeration.CaseType;
import com.configme.domain.enumeration.FormatType;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ComputerCase.
 * Filters: CaseType, Format, Brand
 */
@Entity
@Table(name = "computer_case")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ComputerCase extends Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CaseType type;

    @ElementCollection(targetClass = FormatType.class)
    @CollectionTable(name = "computer_case_formats", joinColumns = @JoinColumn(name = "computer_case_id", referencedColumnName = "id"))
    @Enumerated(EnumType.STRING)
    private Collection<FormatType> formats;

    @NotNull
    @Column(name = "size_max_gpu", nullable = false)
    private Integer sizeMaxGpu;

    @NotNull
    @Column(name = "size_max_ventirad", nullable = false)
    private Integer sizeMaxVentirad;

    @NotNull
    @Column(name = "size_max_psu", nullable = false)
    private Integer sizeMaxPsu;

    @Column(name = "hard_drive_slots")
    private String hardDriveSlots;

    @Column(name = "front_panel_outputs")
    private String frontPanelOutputs;

    @Column(name = "fan_included")
    private String fanIncluded;

    @Column(name = "fan_slots_available")
    private String fanSlotsAvailable;

    @Column(name = "watercooling_compatibility")
    private String watercoolingCompatibility;

    @Embedded
    private Dimension dimension;

    public CaseType getType() {
        return this.type;
    }

    public ComputerCase type(CaseType type) {
        this.type = type;
        return this;
    }

    public void setType(CaseType type) {
        this.type = type;
    }

    public Collection<FormatType> getFormats() {
        return this.formats;
    }

    public ComputerCase formats(Collection<FormatType> formats) {
        this.formats = formats;
        return this;
    }

    public void setFormats(Collection<FormatType> formats) {
        this.formats = formats;
    }

    public Integer getSizeMaxGpu() {
        return this.sizeMaxGpu;
    }

    public ComputerCase sizeMaxGpu(Integer sizeMaxGpu) {
        this.sizeMaxGpu = sizeMaxGpu;
        return this;
    }

    public void setSizeMaxGpu(Integer sizeMaxGpu) {
        this.sizeMaxGpu = sizeMaxGpu;
    }

    public Integer getSizeMaxVentirad() {
        return this.sizeMaxVentirad;
    }

    public ComputerCase sizeMaxVentirad(Integer sizeMaxVentirad) {
        this.sizeMaxVentirad = sizeMaxVentirad;
        return this;
    }

    public void setSizeMaxVentirad(Integer sizeMaxVentirad) {
        this.sizeMaxVentirad = sizeMaxVentirad;
    }

    public Integer getSizeMaxPsu() {
        return this.sizeMaxPsu;
    }

    public ComputerCase sizeMaxPsu(Integer sizeMaxPsu) {
        this.sizeMaxPsu = sizeMaxPsu;
        return this;
    }

    public void setSizeMaxPsu(Integer sizeMaxPsu) {
        this.sizeMaxPsu = sizeMaxPsu;
    }

    public String getHardDriveSlots() {
        return this.hardDriveSlots;
    }

    public ComputerCase hardDriveSlots(String hardDriveSlots) {
        this.hardDriveSlots = hardDriveSlots;
        return this;
    }

    public void setHardDriveSlots(String hardDriveSlots) {
        this.hardDriveSlots = hardDriveSlots;
    }

    public String getFrontPanelOutputs() {
        return this.frontPanelOutputs;
    }

    public ComputerCase frontPanelOutputs(String frontPanelOutputs) {
        this.frontPanelOutputs = frontPanelOutputs;
        return this;
    }

    public void setFrontPanelOutputs(String frontPanelOutputs) {
        this.frontPanelOutputs = frontPanelOutputs;
    }

    public String getFanIncluded() {
        return this.fanIncluded;
    }

    public ComputerCase fanIncluded(String fanIncluded) {
        this.fanIncluded = fanIncluded;
        return this;
    }

    public void setFanIncluded(String fanIncluded) {
        this.fanIncluded = fanIncluded;
    }

    public String getFanSlotsAvailable() {
        return this.fanSlotsAvailable;
    }

    public ComputerCase fanSlotsAvailable(String fanSlotsAvailable) {
        this.fanSlotsAvailable = fanSlotsAvailable;
        return this;
    }

    public void setFanSlotsAvailable(String fanSlotsAvailable) {
        this.fanSlotsAvailable = fanSlotsAvailable;
    }

    public String getWatercoolingCompatibility() {
        return this.watercoolingCompatibility;
    }

    public ComputerCase watercoolingCompatibility(String watercoolingCompatibility) {
        this.watercoolingCompatibility = watercoolingCompatibility;
        return this;
    }

    public void setWatercoolingCompatibility(String watercoolingCompatibility) {
        this.watercoolingCompatibility = watercoolingCompatibility;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public ComputerCase dimension(Dimension dimension) {
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
        if (!(o instanceof ComputerCase)) {
            return false;
        }
        return super.getId() != null && super.getId().equals(((ComputerCase) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComputerCase{" +
            "id=" + super.getId() +
            ", type='" + getType() + "'" +
            ", formats='" + getFormats() + "'" +
            ", sizeMaxGpu=" + getSizeMaxGpu() +
            ", sizeMaxVentirad=" + getSizeMaxVentirad() +
            ", sizeMaxPsu=" + getSizeMaxPsu() +
            ", hardDriveSlots='" + getHardDriveSlots() + "'" +
            ", frontPanelOutputs='" + getFrontPanelOutputs() + "'" +
            ", fanIncluded='" + getFanIncluded() + "'" +
            ", fanSlotsAvailable='" + getFanSlotsAvailable() + "'" +
            ", watercoolingCompatibility='" + getWatercoolingCompatibility() + "'" +
            "}";
    }
}
