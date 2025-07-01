package com.iforddow.pm_backend.utils;


import com.iforddow.pm_backend.exception.BadRequestException;
import com.iforddow.pm_backend.requests.RegisterRequest;

import static com.iforddow.pm_backend.utils.PmUtils.isNullOrEmpty;

public class AuthUtils {

    public static void validateRegistrationRequest(RegisterRequest registerRequest) throws BadRequestException {
        // Check to ensure the email is not null or empty
        if(isNullOrEmpty(registerRequest.getEmail())) {
            throw new BadRequestException("Email is required");
        }

        if(!PmUtils.isValidEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Invalid email format");
        }

        // Check to ensure the password is not null or empty
        if(isNullOrEmpty(registerRequest.getPassword())) {
            throw new BadRequestException("Password is required");
        }

        // Check to ensure confirmation password is not null or empty
        if(isNullOrEmpty(registerRequest.getConfirmPassword())) {
            throw new BadRequestException("Confirmation password is required");
        }

        // Check to ensure the password and confirmation password match
        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }
    }

}
