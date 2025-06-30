package com.iforddow.pm_backend.entity.property;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private String street;
    private String city;
    private String stateProvince;
    private String zipPostalCode;
    private String country;
    private double latitude;
    private double longitude;

    // Constructors, getters, and setters

    public Address(String street, String city, String stateProvince, String zipPostalCode, String country) {
        this.street = street;
        this.city = city;
        this.stateProvince = stateProvince;
        this.zipPostalCode = zipPostalCode;
        this.country = country;
    }

}
