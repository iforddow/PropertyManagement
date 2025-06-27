package com.iforddow.pm_backend.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
* A request object for user registration.
* This class contains fields for the user's
* email, password, and confirmation password.
*
* @author IFD
* @since  2025-06-19
* */
@Data
public class RegisterRequest {

    private @NotNull String email;
    private @NotNull String password;
    private @NotNull String confirmPassword;

}
