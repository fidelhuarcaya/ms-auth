package org.copper.auth.repository;

import org.copper.auth.common.RoleCode;
import org.copper.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByCode(RoleCode code);
}
