package com.iforddow.pm_backend.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User implements UserDetails {
    @Id
    @ColumnDefault("uuid_generate_v4()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "expired", nullable = false)
    private Boolean expired = false;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "locked", nullable = false)
    private Boolean locked = false;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "credentials_expired", nullable = false)
    private Boolean credentialsExpired = false;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "last_active", nullable = false)
    private Instant lastActive;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "User Details \n" +
                "================================\n" +
                "ID: " + id + "\n" +
                "Created At: " + createdAt + "\n" +
                "Email: '" + email + '\'' + "\n" +
                "Password: '" + password + '\'' + "\n" +
                "Expired: " + expired + "\n" +
                "Locked: " + locked + "\n" +
                "Credentials Expired: " + credentialsExpired + "\n" +
                "Enabled: " + enabled + "\n";
    }
}