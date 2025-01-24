package com.GymApl.Security;

import com.GymApl.Entity.EnRole;
import com.GymApl.Entity.Role;
import com.GymApl.Entity.Users;
import com.GymApl.Repository.RoleRepository;
import com.GymApl.Repository.UserRepositoryDefault;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDetailsImplementation implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String username;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

   @Column(nullable = false, columnDefinition = "BIT DEFAULT 1")
    private boolean enabled;



    public static UserDetailsImplementation build (Users user){

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

return new UserDetailsImplementation(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        authorities,
        user.isEnabled());
    }


    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImplementation that = (UserDetailsImplementation) o;
        return Objects.equals(id, that.id);
    }

}



