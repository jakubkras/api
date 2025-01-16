package com.GymApl.Security;

import com.GymApl.Entity.EnRole;
import com.GymApl.Entity.Role;
import com.GymApl.Entity.Users;
import com.GymApl.Repository.UserRepository;
import com.GymApl.Repository.UserRepositoryDefault;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/*@Component
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    UserRepositoryDefault userRepositoryDefault;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepositoryDefault.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("Taka nazwa użytkownika nie istnieje"));

        return UserDetailsImplementation.build(user);

    }
}
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class UserDetailsServiceImplementation implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

    @Autowired
    UserRepositoryDefault userRepositoryDefault;


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Próba zalogowania użytkownika: {}", username);

        Users user = userRepositoryDefault.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Taka nazwa użytkownika nie istnieje"));

        Hibernate.initialize(user.getRoles());



        logger.info("Znaleziono użytkownika: {}", user.getUsername());
        logger.info("Hasło wczytane z bazy: {}", user.getPassword());

        return UserDetailsImplementation.build(user);
    }

}

