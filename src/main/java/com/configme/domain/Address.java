package com.configme.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Address.
 */
@Embeddable
public class Address implements Serializable {

    // private static final long serialVersionUID = 1L;

    @NotNull
    private String zipCode;

    @NotNull
    private String city;

    @NotNull
    private String streetNumber;

    @NotNull
    private String streetName;

    private String complementary;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
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
        Address a = (Address) o;
        //Si les deux sont nuls, alors ils sont égaux
        //Si l'un des deux n'est pas nul alors ils ne sont pas égaux
        //Si les deux sont non nuls alors la valeur est ré-évaluée juste après
        Boolean complementaryEquals = (this.complementary == null) && (a.getComplementary() == null);
        if (this.complementary != null && a.getComplementary() != null) {
            complementaryEquals = a.getComplementary().equals(this.complementary);
        }
        return (
            a.getZipCode().equals(this.zipCode) &&
            a.getCity().equals(this.city) &&
            a.getStreetNumber().equals(this.streetNumber) &&
            a.getStreetName().equals(this.streetName) &&
            complementaryEquals &&
            a.getFirstName().equals(this.firstName) &&
            a.getLastName().equals(this.lastName)
        );
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
            "zipCode='" + getZipCode() + '\'' +
            ", city='" + getCity() + '\'' +
            ", streetNumber='" + getStreetNumber() + '\'' +
            ", streetName='" + getStreetName() + '\'' +
            ", complementary='" + getComplementary() + '\'' +
            ", firstName='" + getFirstName() + '\'' +
            ", lastName='" + getLastName() + '\'' +
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
}
