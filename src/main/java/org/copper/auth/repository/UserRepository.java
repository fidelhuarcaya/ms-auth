package org.copper.auth.repository;

import org.copper.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(@NonNull @Param("email") String email);

    @Query("""
            SELECT u FROM User u LEFT JOIN FETCH u.userRoles
            WHERE u.email = :email
            
            """)
    Optional<User> findByEmail(@Param("email") String email);

}
