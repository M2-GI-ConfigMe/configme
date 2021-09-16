package com.configme.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "street_number", nullable = false)
    private String streetNumber;

    @NotNull
    @Column(name = "street_name", nullable = false)
    private String streetName;

    @Column(name = "complementary")
    private String complementary;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address id(Long id) {
        this.id = id;
        return this;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public Address zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return this.city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetNumber() {
        return this.streetNumber;
    }

    public Address streetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public Address streetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getComplementary() {
        return this.complementary;
    }

    public Address complementary(String complementary) {
        this.complementary = complementary;
        return this;
    }

    public void setComplementary(String complementary) {
        this.complementary = complementary;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Address firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Address lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", zipCode='" + getZipCode() + "'" +
            ", city='" + getCity() + "'" +
            ", streetNumber='" + getStreetNumber() + "'" +
            ", streetName='" + getStreetName() + "'" +
            ", complementary='" + getComplementary() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }

    public static Address of(String firstName, String lastName, String streetNumber, String streetName, String zipCode, String city) {
        Address toReturn = new Address();

        toReturn.setFirstName(firstName);
        toReturn.setLastName(lastName);
        toReturn.setStreetNumber(streetNumber);
        toReturn.setStreetName(streetName);
        toReturn.setCity(city);
        toReturn.setZipCode(zipCode);

        return toReturn;
    }

    public Address clone() {
        Address clone = new Address();
        clone.setComplementary(this.complementary);
        clone.setZipCode(this.zipCode);
        clone.setStreetNumber(this.streetNumber);
        clone.setStreetName(this.streetNumber);
        clone.setCity(this.city);
        clone.setLastName(this.lastName);
        clone.setFirstName(this.firstName);

        return clone;
    }
}
