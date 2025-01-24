package com.GymApl.Security;

import com.GymApl.Entity.EnRole;
import com.GymApl.Entity.Role;
import com.GymApl.Entity.Users;
import com.GymApl.Repository.RoleRepository;
import com.GymApl.Repository.UserRepository;
import com.GymApl.Repository.UserRepositoryDefault;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserServiceImplementation implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

    @Autowired
    UserRepositoryDefault userRepositoryDefault;

    @Autowired
    RoleRepository roleRepository;


    @Override
    public boolean existsByUsername(String username) {
        return userRepositoryDefault.existsByUsername(username);
    }

   @Override
    public void save(Users user) {
        if (user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName(EnRole.USER);
            if (defaultRole == null) {
                defaultRole = new Role(EnRole.USER);
                roleRepository.save(defaultRole);
            }
            user.getRoles().add(defaultRole);
            logger.info("Assigned default role to user: {}", user.getUsername());
        }
        userRepositoryDefault.save(user);
        logger.info("Saved user: {}", user.getUsername());

    }

  /* @Transactional
    public void assignDefaultRoleToExistUser(){


        List<Users> users = userRepositoryDefault.findAll();
        Role defaultRole = roleRepository.findByName(EnRole.USER);

        if(defaultRole==null){
            defaultRole = new Role(EnRole.USER);
            roleRepository.save(defaultRole);
        }

       for(Users user : users){
           if (user.getRoles()== null || user.getRoles().isEmpty()){
               user.getRoles().add(defaultRole);
               userRepositoryDefault.save(user);
               logger.info("Assigned default role to existing user: {}", user.getUsername());

           }
       }
       assignDefaultRoleToExistUser();
    }
*/
}