package com.configme.service.dto;

import com.configme.domain.*;

public class CartDTO {

    private Long cpuId;

    private Long gpuId;

    private Long ramId;

    private Long psuId;

    private Long computerCaseId;

    private Long ventiradId;

    private Long hd1Id;

    private Long hd2Id;

    public Long getCpuId() {
        return cpuId;
    }

    public void setCpuId(Long cpuId) {
        this.cpuId = cpuId;
    }

    public Long getGpuId() {
        return gpuId;
    }

    public void setGpuId(Long gpuId) {
        this.gpuId = gpuId;
    }

    public Long getRamId() {
        return ramId;
    }

    public void setRamId(Long ramId) {
        this.ramId = ramId;
    }

    public Long getPsuId() {
        return psuId;
    }

    public void setPsuId(Long psuId) {
        this.psuId = psuId;
    }

    public Long getComputerCaseId() {
        return computerCaseId;
    }

    public void setComputerCaseId(Long computerCaseId) {
        this.computerCaseId = computerCaseId;
    }

    public Long getVentiradId() {
        return ventiradId;
    }

    public void setVentiradId(Long ventiradId) {
        this.ventiradId = ventiradId;
    }

    public Long getHd1Id() {
        return hd1Id;
    }

    public void setHd1Id(Long hd1Id) {
        this.hd1Id = hd1Id;
    }

    public Long getHd2Id() {
        return hd2Id;
    }

    public void setHd2Id(Long hd2Id) {
        this.hd2Id = hd2Id;
    }
}
