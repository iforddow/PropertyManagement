package com.iforddow.pm_backend.dto;

import com.iforddow.pm_backend.entity.jpa.entity.UserEntity;
import java.util.UUID;

/**
 * A Data Transfer Object (DTO) for User entity.
 *
 * @author IFD
 * @since 2025-06-14
 * */
public record UserDTO(UUID id, String email) {

    /**
     * A constructor to create a UserDTO from a User entity.
     *
     * @param user The User entity to convert.
     *
     * @author IFD
     * @since 2025-06-14
     * */
    public UserDTO(UserEntity user) {
        this(user.getId(), user.getEmail());
    }

}

