package com.iforddow.pm_backend.service;

import com.iforddow.pm_backend.dto.UserDTO;
import com.iforddow.pm_backend.exception.BadRequestException;
import com.iforddow.pm_backend.exception.InvalidCredentialsException;
import com.iforddow.pm_backend.exception.ResourceExistsException;
import com.iforddow.pm_backend.exception.ResourceNotFoundException;
import com.iforddow.pm_backend.jpa.entity.User;
import com.iforddow.pm_backend.repository.UserRepository;
import com.iforddow.pm_backend.requests.LoginRequest;
import com.iforddow.pm_backend.requests.RegisterRequest;
import com.iforddow.pm_backend.utils.PasswordUtils;
import com.iforddow.pm_backend.utils.PmUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    /**
     * A method to handle user registration.
     *
     * @param registerRequest The request object containing user registration details.
     *
     * @return ResponseEntity containing UserDTO if registration is successful.
     *
     * @throws BadRequestException if the request body is invalid.
     *
     * @author IFD
     * @since  2025-06-14
     * */
    @Transactional
    public ResponseEntity<UserDTO> register(RegisterRequest registerRequest) {

        // Check to ensure the email is not null or empty
        if(registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
            throw new BadRequestException("Email is required");
        }

        if(!PmUtils.isValidEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Invalid email format");
        }

        // Check to ensure the password is not null or empty
        if(PmUtils.isNullOrEmpty(registerRequest.getPassword())) {
            throw new BadRequestException("Password is required");
        }

        // Check to ensure confirmation password is not null or empty
        if(PmUtils.isNullOrEmpty(registerRequest.getConfirmPassword())) {
            throw new BadRequestException("Confirmation password is required");
        }

        // Check to ensure the password and confirmation password match
        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        // Validate the password strength
        String validationError = PasswordUtils.validate(registerRequest.getPassword());

        if(validationError != null) {
            throw new BadRequestException(validationError);
        }

        // Check to ensure the password is not null or empty
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());

        // If a user with the same email already exists, throw an exception
        if(existingUser.isPresent()) {
            throw new ResourceExistsException("A user with this email already exists");
        }

        // Create a new user
        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .lastActive(new Date().toInstant())
                .createdAt(new Date().toInstant())
                .build();

        // Save the user to the database
        userRepository.save(user);

        // Create a UserDTO to return
        UserDTO userDTO = new UserDTO(user);

        // Return the UserDTO wrapped in a ResponseEntity
        return ResponseEntity.ok(userDTO);

    }

    /**
     * A method to handle user login.
     *
     * @param loginRequest The request object containing user login details.
     *
     * @return ResponseEntity containing UserDTO if login is successful.
     *
     * @throws ResourceNotFoundException if the user is not found with the provided email.
     *
     * @author IFD
     * @since  2025-06-15
     * */
    public ResponseEntity<Map<String, Object>> login(LoginRequest loginRequest, HttpServletResponse response) {

        Optional<User> user = userRepository.findByEmail(loginRequest.getUsername());

        // Check if the user exists
        if(user.isEmpty()) {
            throw new ResourceNotFoundException("User not found with email: " + loginRequest.getUsername());
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (AuthenticationException ex) {

            if(ex instanceof BadCredentialsException) {
                throw new InvalidCredentialsException("Invalid credentials provided");
            }
            // Log or return the specific error
            throw new BadRequestException("Authentication failed: " + ex.getMessage());
        }

        String accessToken = jwtService.generateJwtToken(loginRequest.getUsername());
        String refreshToken = jwtService.generateRefreshToken(loginRequest.getUsername());

        Cookie refreshCookie = new Cookie("pm_rt", refreshToken);

        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(jwtService.jwtRefreshExpirationMs / 1000);
        refreshCookie.setSecure(true);

        response.addCookie(refreshCookie);

        //Make UserDTO
        UserDTO userDTO = new UserDTO(user.get());

        return ResponseEntity.ok(Map.of("user", userDTO, "accessToken", accessToken));

    }

    /**
     * A method to handle token refresh requests.
     *
     * @param refreshToken The refresh token from the request cookie.
     * @return ResponseEntity containing the new access token if refresh is successful.
     *
     * @throws BadRequestException if the refresh token is invalid.
     *
     * @author IFD
     * @since  2025-06-15
     * */
    public ResponseEntity<Map<String, Object>> refreshToken(String refreshToken) {

        if (jwtService.validateJwtToken(refreshToken)) {

            String username = jwtService.getUsernameFromToken(refreshToken);
            String newAccessToken = jwtService.generateJwtToken(username);

            // Get the user from the database
            Optional<User> user = userRepository.findByEmail(username);

            if (user.isEmpty()) {
                throw new ResourceNotFoundException("User not found with email: " + username);
            }

            // Update the user's last active time
            user.get().setLastActive(new Date().toInstant());

            // Save the updated user back to the database
            userRepository.save(user.get());

            UserDTO userDTO = new UserDTO(user.get());

            return ResponseEntity.ok(Map.of("accessToken", newAccessToken, "user", userDTO));

        } else {

            throw new BadRequestException("Invalid refresh token");

        }
    }

}
