package com.GymApl.Controller;


import com.GymApl.Entity.Users;
import com.GymApl.Service.UserService;
import com.GymApl.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

   @Autowired
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Validated @RequestBody UserDto user){

    userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Użytkownik został pomyslnie dodany");
    }


    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable UUID id){
        Optional<Users> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id) {

        try {
            userService.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
        @GetMapping("/username/{username}")
        public ResponseEntity<Users> getUserByUsername(@PathVariable String username) {
    Optional<Users> users = userService.getUserByUsername(username);

            return users.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(null));
        }


        @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUserByID(@PathVariable UUID id, @RequestBody Users user) {
            try {
                user.setId(id);
                userService.updateUser(user);
                return ResponseEntity.status(HttpStatus.OK).body("Użytkownik został zaaktualizowany");
            } catch (IllegalArgumentException e) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }
       @PutMapping("/disable/{id}")
    public ResponseEntity <String> disableUserById(@PathVariable UUID id){
        try{
        userService.disableUserById(id);

           return ResponseEntity.status(HttpStatus.OK)
                   .body("User disabled successfully");
       } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}

    @PutMapping("/enable/{id}")
    public ResponseEntity <String> enableUserById(@PathVariable UUID id){
        try{
            userService.enableUserById(id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body("User enabled successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


}


