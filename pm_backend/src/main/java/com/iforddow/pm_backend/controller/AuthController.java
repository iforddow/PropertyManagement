package com.iforddow.pm_backend.controller;
import com.iforddow.pm_backend.requests.LoginRequest;
import com.iforddow.pm_backend.requests.RegisterRequest;
import com.iforddow.pm_backend.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return authService.login(loginRequest, response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        return authService.register(registerRequest, response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@CookieValue("pm_rt") String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

}
