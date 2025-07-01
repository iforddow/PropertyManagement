package com.iforddow.pm_backend.repository;

import com.iforddow.pm_backend.entity.jpa.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {



}
