package com.GymApl.Repository;

import com.GymApl.Entity.EnRole;
import com.GymApl.Entity.Role;
import com.GymApl.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryDefault extends JpaRepository<Users, UUID> {
    Boolean existsByUsername (String username);
    Optional<Users> findByUsername (String username);
}
