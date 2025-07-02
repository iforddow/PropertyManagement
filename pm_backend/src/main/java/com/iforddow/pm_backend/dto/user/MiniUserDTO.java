package com.iforddow.pm_backend.dto.user;

import com.iforddow.pm_backend.entity.entity.user.User;

import java.util.UUID;

/**
 * A Data Transfer Object (DTO) for User entity.
 *
 * @author IFD
 * @since 2025-06-14
 * */
public record MiniUserDTO(UUID id, String email) {

    /**
    * A constructor to create a UserDTO from a User entity.
    *
    * @param user The User entity to convert.
    *
    * @author IFD
    * @since 2025-07-01
    * */
    public MiniUserDTO(User user) {
        this(user.getId(), user.getEmail());
    }

}

