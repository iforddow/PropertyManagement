package com.iforddow.pm_backend.entity.entity.property;
import com.iforddow.pm_backend.enums.PropertyType;
import com.iforddow.pm_backend.enums.TimePeriod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Property {

    private Address address;
    private PropertyType propertyType;
    private double rentalPrice;
    private TimePeriod rentOccurrence;
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private int parkingSpaces;
    private double squareFootage;

    private String description;

    // Constructors, getters, and setters

    public Property(Address address, PropertyType propertyType, double rentalPrice) {
        this.address = address;
        this.propertyType = propertyType;
        this.rentalPrice = rentalPrice;
    }

}
