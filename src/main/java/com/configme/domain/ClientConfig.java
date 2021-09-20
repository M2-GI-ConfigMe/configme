package com.configme.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ClientConfig.
 */
@Entity
@Table(name = "client_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClientConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tags")
    private String tags;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_comlete")
    private Boolean isComlete;

    @Column(name = "research_key")
    private String researchKey;

    @Column(name = "cpu_price")
    private Float cpuPrice;

    @Column(name = "gpu_price")
    private Float gpuPrice;

    @Column(name = "ram_1_price")
    private Float ram1Price;

    @Column(name = "ram_2_price")
    private Float ram2Price;

    @Column(name = "psu_price")
    private Float psuPrice;

    @Column(name = "mbe_price")
    private Float mbePrice;

    @Column(name = "computer_case_price")
    private Float computerCasePrice;

    @Column(name = "ventirad_price")
    private Float ventiradPrice;

    @Column(name = "hd_1_price")
    private Float hd1Price;

    @Column(name = "hd_2_price")
    private Float hd2Price;

    @ManyToOne
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Cpu cpu;

    @ManyToOne(fetch = FetchType.EAGER)
    private Gpu gpu;

    @ManyToOne(fetch = FetchType.EAGER)
    private Psu psu;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ventirad ventirad;

    @ManyToOne(fetch = FetchType.EAGER)
    private Mbe mbe;

    @ManyToOne(fetch = FetchType.EAGER)
    private ComputerCase computerCase;

    @ManyToOne(fetch = FetchType.EAGER)
    private HardDrive deadMemory1;

    @ManyToOne(fetch = FetchType.EAGER)
    private HardDrive deadMemory2;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ram ram1;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ram ram2;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientConfig id(Long id) {
        this.id = id;
        return this;
    }

    public String getTags() {
        return this.tags;
    }

    public ClientConfig tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getName() {
        return this.name;
    }

    public ClientConfig name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public ClientConfig description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsComlete() {
        return this.isComlete;
    }

    public ClientConfig isComlete(Boolean isComlete) {
        this.isComlete = isComlete;
        return this;
    }

    public void setIsComlete(Boolean isComlete) {
        this.isComlete = isComlete;
    }

    public String getResearchKey() {
        return this.researchKey;
    }

    public ClientConfig researchKey(String researchKey) {
        this.researchKey = researchKey;
        return this;
    }

    public void setResearchKey(String researchKey) {
        this.researchKey = researchKey;
    }

    public Float getCpuPrice() {
        return this.cpuPrice;
    }

    public ClientConfig cpuPrice(Float cpuPrice) {
        this.cpuPrice = cpuPrice;
        return this;
    }

    public void setCpuPrice(Float cpuPrice) {
        this.cpuPrice = cpuPrice;
    }

    public Float getGpuPrice() {
        return this.gpuPrice;
    }

    public ClientConfig gpuPrice(Float gpuPrice) {
        this.gpuPrice = gpuPrice;
        return this;
    }

    public void setGpuPrice(Float gpuPrice) {
        this.gpuPrice = gpuPrice;
    }

    public Float getRam1Price() {
        return this.ram1Price;
    }

    public ClientConfig ram1Price(Float ram1Price) {
        this.ram1Price = ram1Price;
        return this;
    }

    public void setRam1Price(Float ram1Price) {
        this.ram1Price = ram1Price;
    }

    public Float getRam2Price() {
        return this.ram2Price;
    }

    public ClientConfig ram2Price(Float ram2Price) {
        this.ram2Price = ram2Price;
        return this;
    }

    public void setRam2Price(Float ram2Price) {
        this.ram2Price = ram2Price;
    }

    public Float getPsuPrice() {
        return this.psuPrice;
    }

    public ClientConfig psuPrice(Float psuPrice) {
        this.psuPrice = psuPrice;
        return this;
    }

    public void setPsuPrice(Float psuPrice) {
        this.psuPrice = psuPrice;
    }

    public Float getComputerCasePrice() {
        return this.computerCasePrice;
    }

    public ClientConfig computerCasePrice(Float computerCasePrice) {
        this.computerCasePrice = computerCasePrice;
        return this;
    }

    public void setComputerCasePrice(Float computerCasePrice) {
        this.computerCasePrice = computerCasePrice;
    }

    public Float getVentiradPrice() {
        return this.ventiradPrice;
    }

    public ClientConfig ventiradPrice(Float ventiradPrice) {
        this.ventiradPrice = ventiradPrice;
        return this;
    }

    public void setVentiradPrice(Float ventiradPrice) {
        this.ventiradPrice = ventiradPrice;
    }

    public Float getHd1Price() {
        return this.hd1Price;
    }

    public ClientConfig hd1Price(Float hd1Price) {
        this.hd1Price = hd1Price;
        return this;
    }

    public void setHd1Price(Float hd1Price) {
        this.hd1Price = hd1Price;
    }

    public Float getHd2Price() {
        return this.hd2Price;
    }

    public ClientConfig hd2Price(Float hd2Price) {
        this.hd2Price = hd2Price;
        return this;
    }

    public void setHd2Price(Float hd2Price) {
        this.hd2Price = hd2Price;
    }

    public Cpu getCpu() {
        return this.cpu;
    }

    public ClientConfig cpu(Cpu cpu) {
        this.setCpu(cpu);
        return this;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Gpu getGpu() {
        return this.gpu;
    }

    public ClientConfig gpu(Gpu gpu) {
        this.setGpu(gpu);
        return this;
    }

    public void setGpu(Gpu gpu) {
        this.gpu = gpu;
    }

    public Psu getPsu() {
        return this.psu;
    }

    public ClientConfig psu(Psu psu) {
        this.setPsu(psu);
        return this;
    }

    public void setPsu(Psu psu) {
        this.psu = psu;
    }

    public Ventirad getVentirad() {
        return this.ventirad;
    }

    public ClientConfig ventirad(Ventirad ventirad) {
        this.setVentirad(ventirad);
        return this;
    }

    public void setVentirad(Ventirad ventirad) {
        this.ventirad = ventirad;
    }

    public Mbe getMbe() {
        return this.mbe;
    }

    public ClientConfig mbe(Mbe mbe) {
        this.setMbe(mbe);
        return this;
    }

    public void setMbe(Mbe mbe) {
        this.mbe = mbe;
    }

    public ComputerCase getComputerCase() {
        return this.computerCase;
    }

    public ClientConfig computerCase(ComputerCase computerCase) {
        this.setComputerCase(computerCase);
        return this;
    }

    public void setComputerCase(ComputerCase computerCase) {
        this.computerCase = computerCase;
    }

    public HardDrive getDeadMemory1() {
        return this.deadMemory1;
    }

    public ClientConfig deadMemory1(HardDrive hardDrive) {
        this.setDeadMemory1(hardDrive);
        return this;
    }

    public void setDeadMemory1(HardDrive hardDrive) {
        this.deadMemory1 = hardDrive;
    }

    public HardDrive getDeadMemory2() {
        return this.deadMemory2;
    }

    public ClientConfig deadMemory2(HardDrive hardDrive) {
        this.setDeadMemory2(hardDrive);
        return this;
    }

    public void setDeadMemory2(HardDrive hardDrive) {
        this.deadMemory2 = hardDrive;
    }

    public Ram getRam1() {
        return this.ram1;
    }

    public ClientConfig ram1(Ram ram) {
        this.setRam1(ram);
        return this;
    }

    public void setRam1(Ram ram) {
        this.ram1 = ram;
    }

    public Ram getRam2() {
        return this.ram2;
    }

    public ClientConfig ram2(Ram ram) {
        this.setRam2(ram);
        return this;
    }

    public Float getMbePrice() {
        return mbePrice;
    }

    public void setMbePrice(Float mbePrice) {
        this.mbePrice = mbePrice;
    }

    public void setRam2(Ram ram) {
        this.ram2 = ram;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientConfig)) {
            return false;
        }
        return id != null && id.equals(((ClientConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientConfig{" +
            "id=" + getId() +
            ", tags='" + getTags() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", isComlete='" + getIsComlete() + "'" +
            ", researchKey='" + getResearchKey() + "'" +
            ", cpuPrice=" + getCpuPrice() +
            ", gpuPrice=" + getGpuPrice() +
            ", ram1Price=" + getRam1Price() +
            ", ram2Price=" + getRam2Price() +
            ", psuPrice=" + getPsuPrice() +
            ", computerCasePrice=" + getComputerCasePrice() +
            ", ventiradPrice=" + getVentiradPrice() +
            ", hd1Price=" + getHd1Price() +
            ", hd2Price=" + getHd2Price() +
            "}";
    }
}
