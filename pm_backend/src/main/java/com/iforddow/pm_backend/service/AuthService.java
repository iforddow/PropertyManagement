package com.iforddow.pm_backend.service;

import com.iforddow.pm_backend.dto.user.UserDTO;
import com.iforddow.pm_backend.entity.jpa.entity.UserAuth;
import com.iforddow.pm_backend.entity.jpa.entity.UserProfile;
import com.iforddow.pm_backend.entity.mappers.UserMapper;
import com.iforddow.pm_backend.exception.BadRequestException;
import com.iforddow.pm_backend.exception.InvalidCredentialsException;
import com.iforddow.pm_backend.exception.ResourceExistsException;
import com.iforddow.pm_backend.exception.ResourceNotFoundException;
import com.iforddow.pm_backend.repository.UserAuthRepository;
import com.iforddow.pm_backend.repository.UserProfileRepository;
import com.iforddow.pm_backend.requests.LoginRequest;
import com.iforddow.pm_backend.requests.RegisterRequest;
import com.iforddow.pm_backend.utils.PasswordUtils;
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
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static com.iforddow.pm_backend.utils.AuthUtils.validateRegistrationRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAuthRepository userAuthRepository;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserService userService;

    private final UserProfileRepository userProfileRepository;

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
    public ResponseEntity<Map<String, Object>> register(RegisterRequest registerRequest, HttpServletResponse response) {

        validateRegistrationRequest(registerRequest);

        // Validate the password strength
        String validationError = PasswordUtils.validate(registerRequest.getPassword());

        if(validationError != null) {
            throw new BadRequestException(validationError);
        }

        // Check to ensure the password is not null or empty
        Optional<UserAuth> existingUser = userAuthRepository.findByEmail(registerRequest.getEmail());

        // If a user with the same email already exists, throw an exception
        if(existingUser.isPresent()) {
            throw new ResourceExistsException("A user with this email already exists");
        }

        // Create a new user
        userService.createUserWithProfile(registerRequest);

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail(registerRequest.getEmail());
        loginRequest.setPassword(registerRequest.getPassword());

        return login(loginRequest, response);

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

        Optional<UserAuth> userAuth = userAuthRepository.findByEmail(loginRequest.getEmail());

        // Check if the user exists
        if(userAuth.isEmpty()) {
            throw new ResourceNotFoundException("User not found with email: " + loginRequest.getEmail());
        }

        Optional<UserProfile> userProfile = userProfileRepository.findById(userAuth.get().getId());

        if(userProfile.isEmpty()) {
            throw new ResourceNotFoundException("User profile not found for user with email: " + loginRequest.getEmail());
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (AuthenticationException ex) {

            if(ex instanceof BadCredentialsException) {
                throw new InvalidCredentialsException("Invalid credentials provided");
            }
            // Log or return the specific error
            throw new BadRequestException("Authentication failed: " + ex.getMessage());
        }

        String accessToken = jwtService.generateJwtToken(loginRequest.getEmail());
        String refreshToken = jwtService.generateRefreshToken(loginRequest.getEmail());

        Cookie refreshCookie = new Cookie("pm_rt", refreshToken);

        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(jwtService.jwtRefreshExpirationMs / 1000);
        refreshCookie.setSecure(true);

        response.addCookie(refreshCookie);

        //Make UserDTO
        UserDTO userDTO = userMapper.toDTO(userMapper.toUser(userAuth.get(), userProfile.get()));

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
            Optional<UserAuth> userAuth = userAuthRepository.findByEmail(username);

            if (userAuth.isEmpty()) {
                throw new ResourceNotFoundException("User not found with email: " + username);
            }

            Optional<UserProfile> userProfile = userProfileRepository.findById(userAuth.get().getId());

            if (userProfile.isEmpty()) {
                throw new ResourceNotFoundException("User profile not found for user with email: " + username);
            }

            // Update the user's last active time
            userAuth.get().setLastActive(new Date().toInstant());

            // Save the updated user back to the database
            userAuthRepository.save(userAuth.get());

            UserDTO userDTO = userMapper.toDTO(userMapper.toUser(userAuth.get(), userProfile.get()));

            return ResponseEntity.ok(Map.of("accessToken", newAccessToken, "user", userDTO));

        } else {

            throw new BadRequestException("Invalid refresh token");

        }
    }

    /**
     * A method to handle user logout.
     *
     * @param refreshToken The refresh token from the request cookie.
     * @return ResponseEntity indicating successful logout.
     *
     * @throws BadRequestException if the refresh token is invalid.
     *
     * @author IFD
     * @since  2025-06-15
     * */
    public ResponseEntity<Map<String, Object>> logout(String refreshToken, HttpServletResponse response) {

        if (jwtService.validateJwtToken(refreshToken)) {

            // Invalidate the refresh token by setting it to null
            Cookie cookie = new Cookie("pm_rt", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            cookie.setSecure(true);

            response.addCookie(cookie);

            return ResponseEntity.ok(Map.of("message", "Logout successful"));

        } else {

            throw new BadRequestException("Invalid refresh token");

        }
    }

}
