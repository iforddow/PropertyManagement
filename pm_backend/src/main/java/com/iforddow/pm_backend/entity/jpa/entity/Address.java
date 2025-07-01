package com.iforddow.pm_backend.entity.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @ColumnDefault("uuid_generate_v4()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 255)
    @Column(name = "street_one")
    private String streetOne;

    @Size(max = 255)
    @Column(name = "street_two")
    private String streetTwo;

    @Size(max = 255)
    @Column(name = "city_town")
    private String cityTown;

    @Size(max = 255)
    @Column(name = "state_province")
    private String stateProvince;

    @Size(max = 255)
    @Column(name = "postal_zip_code")
    private String postalZipCode;

    @Size(max = 255)
    @Column(name = "country")
    private String country;

    @Size(max = 10)
    @Column(name = "unit_suite", length = 10)
    private String unitSuite;

    @Column(name = "location", columnDefinition = "geography(Point,4326)")
    private Point location;

}