package com.aeguinazu.bookstore.repositories;

import com.aeguinazu.bookstore.models.usersentities.ERole;
import com.aeguinazu.bookstore.models.usersentities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
