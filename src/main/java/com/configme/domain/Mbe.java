package com.configme.domain;

import com.configme.domain.enumeration.FormatType;
import com.configme.domain.enumeration.RamType;
import com.configme.domain.enumeration.SocketType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Mbe.
 * Filters: Socket, RamType, FormatTYpe, Brand
 */
@Entity
@Table(name = "mbe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mbe extends Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "socket_cpu", nullable = false)
    private SocketType socketCpu;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ram_type", nullable = false)
    private RamType ramType;

    @NotNull
    @Column(name = "ram_frequency_max", nullable = false)
    private Float ramFrequencyMax;

    @NotNull
    @Column(name = "ram_size_max", nullable = false)
    private Float ramSizeMax;

    @NotNull
    @Column(name = "pci_outputs", nullable = false)
    private String pciOutputs;

    @Column(name = "display_output")
    private String displayOutput;

    @Column(name = "storage_output")
    private String storageOutput;

    @Column(name = "inside_io")
    private String insideIO;

    @Column(name = "back_panel_output")
    private String backPanelOutput;

    @Column(name = "bios")
    private String bios;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false)
    private FormatType format;

    public SocketType getSocketCpu() {
        return this.socketCpu;
    }

    public Mbe socketCpu(SocketType socketCpu) {
        this.socketCpu = socketCpu;
        return this;
    }

    public void setSocketCpu(SocketType socketCpu) {
        this.socketCpu = socketCpu;
    }

    public RamType getRamType() {
        return this.ramType;
    }

    public Mbe ramType(RamType ramType) {
        this.ramType = ramType;
        return this;
    }

    public void setRamType(RamType ramType) {
        this.ramType = ramType;
    }

    public Float getRamFrequencyMax() {
        return this.ramFrequencyMax;
    }

    public Mbe ramFrequencyMax(Float ramFrequencyMax) {
        this.ramFrequencyMax = ramFrequencyMax;
        return this;
    }

    public void setRamFrequencyMax(Float ramFrequencyMax) {
        this.ramFrequencyMax = ramFrequencyMax;
    }

    public Float getRamSizeMax() {
        return this.ramSizeMax;
    }

    public Mbe ramSizeMax(Float ramSizeMax) {
        this.ramSizeMax = ramSizeMax;
        return this;
    }

    public void setRamSizeMax(Float ramSizeMax) {
        this.ramSizeMax = ramSizeMax;
    }

    public String getPciOutputs() {
        return this.pciOutputs;
    }

    public Mbe pciOutputs(String pciOutputs) {
        this.pciOutputs = pciOutputs;
        return this;
    }

    public void setPciOutputs(String pciOutputs) {
        this.pciOutputs = pciOutputs;
    }

    public String getDisplayOutput() {
        return this.displayOutput;
    }

    public Mbe displayOutput(String displayOutput) {
        this.displayOutput = displayOutput;
        return this;
    }

    public void setDisplayOutput(String displayOutput) {
        this.displayOutput = displayOutput;
    }

    public String getStorageOutput() {
        return this.storageOutput;
    }

    public Mbe storageOutput(String storageOutput) {
        this.storageOutput = storageOutput;
        return this;
    }

    public void setStorageOutput(String storageOutput) {
        this.storageOutput = storageOutput;
    }

    public String getInsideIO() {
        return this.insideIO;
    }

    public Mbe insideIO(String insideIO) {
        this.insideIO = insideIO;
        return this;
    }

    public void setInsideIO(String insideIO) {
        this.insideIO = insideIO;
    }

    public String getBackPanelOutput() {
        return this.backPanelOutput;
    }

    public Mbe backPanelOutput(String backPanelOutput) {
        this.backPanelOutput = backPanelOutput;
        return this;
    }

    public void setBackPanelOutput(String backPanelOutput) {
        this.backPanelOutput = backPanelOutput;
    }

    public String getBios() {
        return this.bios;
    }

    public Mbe bios(String bios) {
        this.bios = bios;
        return this;
    }

    public void setBios(String bios) {
        this.bios = bios;
    }

    public FormatType getFormat() {
        return this.format;
    }

    public Mbe format(FormatType format) {
        this.format = format;
        return this;
    }

    public void setFormat(FormatType format) {
        this.format = format;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mbe)) {
            return false;
        }
        return super.getId() != null && super.getId().equals(((Mbe) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mbe{" +
            "id=" + super.getId() +
            ", socketCpu='" + getSocketCpu() + "'" +
            ", ramType='" + getRamType() + "'" +
            ", ramFrequencyMax=" + getRamFrequencyMax() +
            ", ramSizeMax=" + getRamSizeMax() +
            ", pciOutputs='" + getPciOutputs() + "'" +
            ", displayOutput='" + getDisplayOutput() + "'" +
            ", storageOutput='" + getStorageOutput() + "'" +
            ", insideIO='" + getInsideIO() + "'" +
            ", backPanelOutput='" + getBackPanelOutput() + "'" +
            ", bios='" + getBios() + "'" +
            ", format='" + getFormat() + "'" +
            "}";
    }
}
