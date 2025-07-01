package com.iforddow.pm_backend.entity.entity.user;

import com.iforddow.pm_backend.entity.jpa.entity.Address;
import com.iforddow.pm_backend.enums.ProfileType;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class User {

    // Authentication related fields
    private UUID id;
    private Instant createdAt;
    private String email;
    private String password;
    private boolean expired;
    private boolean locked;
    private boolean credentialsExpired;
    private boolean enabled;
    private Instant lastActive;

    //Profile related fields
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureUrl;
    private Address address;
    private ProfileType profileType;


    @Override
    public String toString() {
        return "User Details \n" +
                "================================\n" +
                "ID: " + id + "\n" +
                "Created At: " + createdAt + "\n" +
                "Email: '" + email + '\'' + "\n" +
                "Password: '" + password + '\'' + "\n" +
                "Expired: " + expired + "\n" +
                "Locked: " + locked + "\n" +
                "Credentials Expired: " + credentialsExpired + "\n" +
                "Enabled: " + enabled + "\n" +
                "Last Active: " + lastActive + "\n" +
                "First Name: '" + firstName + '\'' + "\n" +
                "Last Name: '" + lastName + '\'' + "\n" +
                "Phone Number: '" + phoneNumber + '\'' + "\n" +
                "Profile Picture URL: '" + profilePictureUrl + '\'' + "\n" +
                "Address: " + address + "\n" +
                "Profile Type: " + profileType + "\n";
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof User user)) return false;
        return id != null && id.equals(user.id);
    }

}
