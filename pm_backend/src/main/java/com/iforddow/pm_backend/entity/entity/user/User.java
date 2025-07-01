package com.iforddow.pm_backend.entity.user;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class User {

    private UUID id;
    private Instant createdAt;
    private String email;
    private String password;
    private boolean expired;
    private boolean locked;
    private boolean credentialsExpired;
    private boolean enabled;
    private Instant lastActive;

}
