package com.iforddow.pm_backend.service;

import com.iforddow.pm_backend.entity.jpa.entity.UserAuth;
import com.iforddow.pm_backend.entity.jpa.entity.UserProfile;
import com.iforddow.pm_backend.repository.UserAuthRepository;
import com.iforddow.pm_backend.repository.UserProfileRepository;
import com.iforddow.pm_backend.requests.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserAuthRepository userAuthRepository;

    private final UserProfileRepository userProfileRepository;

    public void createUserWithProfile(RegisterRequest registerRequest) {
        UserAuth user = UserAuth.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .lastActive(new Date().toInstant())
                .createdAt(new Date().toInstant())
                .credentialsExpired(false)
                .enabled(true)
                .locked(false)
                .expired(false)
                .emailConfirmed(false)
                .build();

        userAuthRepository.save(user);

        UserProfile userProfile = UserProfile.builder().userAuth(user).profileSetup(false).build();

        userProfileRepository.save(userProfile);
    }

}
