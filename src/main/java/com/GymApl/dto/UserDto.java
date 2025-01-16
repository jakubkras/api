package com.GymApl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
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
    private String first_name;

    @NotBlank(message = "Nazwisko nie może być puste")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Nazwisko musi się zaczynać wielką literą")
    @Size(max = 30, message = "Nazwisko musi może mieć maksymalnie 30 znaków")
    private String last_name;


}
