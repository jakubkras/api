package com.GymApl.Security;


import com.GymApl.Entity.Users;
import org.apache.catalina.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


    boolean existsByUsername(String username);
    void save (Users user);
}
