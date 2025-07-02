package com.iforddow.pm_backend.dto.user;

import com.iforddow.pm_backend.entity.entity.property.Address;
import com.iforddow.pm_backend.entity.entity.user.User;
import com.iforddow.pm_backend.enums.ProfileType;

import java.time.Instant;
import java.util.UUID;

public record FullUserDTO(UUID id, String email,
                          Instant createdAt, boolean expired, boolean locked,
                          boolean credentialsExpired, boolean enabled,
                          Instant lastActive, boolean emailConfirmed,
                          String firstName, String lastName,
                          String phoneNumber, String profilePictureUrl,
                          Address address, ProfileType profileType, boolean profileSetup) {

    /**
     * A constructor to create a FullUserDTO from a User entity.
     *
     * @param user The User entity to convert.
     *
     * @author IFD
     * @since 2025-07-01
     */
    public FullUserDTO(User user) {
        this(user.getId(), user.getEmail(), user.getCreatedAt(), user.isExpired(), user.isLocked(), user.isCredentialsExpired(),
                user.isEnabled(), user.getLastActive(), user.isEmailConfirmed(),
                user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getProfilePictureUrl(),
                user.getAddress(), user.getProfileType(), user.isProfileSetup());
    }

}
