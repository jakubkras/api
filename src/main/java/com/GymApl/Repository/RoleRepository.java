package com.GymApl.Repository;



import com.GymApl.Entity.EnRole;
import com.GymApl.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(EnRole name);
}