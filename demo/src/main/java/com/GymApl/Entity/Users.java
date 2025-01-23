package com.GymApl.Entity;


import com.GymApl.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name="users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    public Users(UserDto userDto) {
        this.id = UUID.randomUUID();
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.firstName = userDto.getFirst_name();
        this.lastName = userDto.getLast_name();
        this.join_date = LocalDate.now();
    }

    @Id
    // dla MS SQL  @Column(columnDefinition = "UNIQUEIDENTIFIER")
   @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

@NotBlank(message = "Hasło nie może być pusta")
@Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,30}$",
        message = "Hasło musi mieć od 8 do 30 znaków, musi zawierać jedną wielką literę, jedną małą i jedną cyfrę "
)    private String password;

    @NotBlank(message = "Nazwa użytkownika nie może być puste")
    @Size (min = 3,max = 20, message = "Nazwa użytkownika musi mieć od 3 do 20 znaków")
    private String username;

    @NotBlank(message = "Imie nie może być puste")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Imie musi się zaczynać wielką literą")
    @Size(max = 30, message = "Imię musi może mieć maksymalnie 30 znaków")
    private String firstName;

    @NotBlank(message = "Nazwisko nie może być puste")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Nazwisko musi się zaczynać wielką literą")
    @Size(max = 30, message = "Nazwisko musi może mieć maksymalnie 30 znaków")
    private String lastName;
    private LocalDate join_date;

    @OneToMany (mappedBy = "userID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Training> trainings = new ArrayList<>();


  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.REMOVE})
  @JoinTable(  name = "user_roles",
  joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

   private boolean enabled;

   public boolean isEnabled(){
       return  this.enabled = true;
   }

}

