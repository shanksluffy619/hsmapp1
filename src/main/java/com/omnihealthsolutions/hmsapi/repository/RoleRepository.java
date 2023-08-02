package com.omnihealthsolutions.hmsapi.repository;

import com.omnihealthsolutions.hmsapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
