package com.iforddow.pm_backend.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * A request object for user login containing username and password.
 * This class is used to encapsulate the login credentials provided by the user.
 *
 * @author IFD
 * @since  2025-06-19
 */
@Data
public class LoginRequest {

    private @NotNull String email;
    private @NotNull String password;

}
