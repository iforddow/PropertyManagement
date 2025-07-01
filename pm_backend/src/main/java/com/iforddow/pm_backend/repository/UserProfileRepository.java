package com.iforddow.pm_backend.repository;

import com.iforddow.pm_backend.entity.jpa.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {



}
