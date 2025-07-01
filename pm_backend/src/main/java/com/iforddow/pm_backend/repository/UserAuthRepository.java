package com.iforddow.pm_backend.repository;
import com.iforddow.pm_backend.entity.jpa.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAuthRepository extends JpaRepository<UserAuth, UUID> {

    Optional<UserAuth> findByEmail(String email);

}
