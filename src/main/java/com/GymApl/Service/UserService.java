package com.GymApl.Service;


import com.GymApl.Repository.UserRepository;
import com.GymApl.Entity.Users;
import com.GymApl.Repository.UserRepositoryDefault;
import com.GymApl.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private final UserRepositoryDefault userRepositoryDefault;

    @Autowired
    public UserService(UserRepository userRepository, UserRepositoryDefault userRepositoryDefault) {

        this.userRepository = userRepository;
        this.userRepositoryDefault = userRepositoryDefault;
    }

    public Optional<Users> getUserById (UUID id){

        return userRepository.findById(id);
    }

    public Optional<Users> getUserByUsername(String username){

        return userRepository.findByUsername(username);
    }

    public List<Users> getAllUsers(){

        return userRepository.findAll();
    }


    public Users updateUser(Users user) {
        Optional<Users> existingUserOpt = userRepository.findById(user.getId());

        if (existingUserOpt.isEmpty()) {
            throw new IllegalArgumentException("Nie ma użytkownika o takim id: " + user.getId());
        }

        Users existingUser = existingUserOpt.get();

        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
        existingUser.setPassword(user.getPassword());
    }
        userRepository.update(existingUser);
        return existingUser;
    }


    public Users createUser(UserDto user) {
            Optional<Users> existingUser = userRepository.findByUsername(user.getUsername());

            if (existingUser.isPresent()) {
                throw new IllegalArgumentException("Taki użytkownik już istnieje");
            }

            Users users = new Users(user);
            return userRepository.create(users);

    }

    public void deleteUserById(UUID id){
        Optional<Users> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new IllegalArgumentException("Użytkownik z takim id nie istnieje");
        }
        userRepository.deleteById(id);

    }


    public void disableUserById(UUID id){
        Optional<Users> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new IllegalArgumentException("Użytkownik z takim id nie istnieje");
        }
        Users user = userOptional.get();

            user.setEnabled(false);
         userRepositoryDefault.save(user);
        userRepository.disable(id);

    }

    public void enableUserById(UUID id){
        Optional<Users> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new IllegalArgumentException("Użytkownik z takim id nie istnieje");
        }
        Users user = userOptional.get();

        user.setEnabled(true);
        userRepositoryDefault.save(user);
        userRepository.enable(id);

    }
}
