package com.configme.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Dimension.
 */
@Embeddable
public class Dimension implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer height;

    @NotNull
    private Integer width;

    @NotNull
    private Integer length;

    public Dimension() {}

    public Dimension(int height, int width, int length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Dimension height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return this.width;
    }

    public Dimension width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLength() {
        return this.length;
    }

    public Dimension length(Integer length) {
        this.length = length;
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Dimension)) return false;

        Dimension d = (Dimension) o;
        return d.getHeight().equals(this.height) && d.getWidth().equals(this.width) && d.getLength().equals(this.length);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dimension{" +
            "height=" + getHeight() +
            ", width=" + getWidth() +
            ", length=" + getLength() +
            "}";
    }
}
