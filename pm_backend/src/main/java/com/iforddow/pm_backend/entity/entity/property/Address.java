package com.iforddow.pm_backend.entity.entity.property;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Getter
@Setter
public class Address {

    private UUID id;
    private String street;
    private String city;
    private String stateProvince;
    private String zipPostalCode;
    private String country;
    private Point location;
    private String unitSuite;

    // Constructors, getters, and setters

    public Address(String street, String city, String stateProvince, String zipPostalCode, String country, Point location, String unitSuite) {
        this.street = street;
        this.city = city;
        this.stateProvince = stateProvince;
        this.zipPostalCode = zipPostalCode;
        this.country = country;
        this.location = location;
        this.unitSuite = unitSuite;
    }

}
