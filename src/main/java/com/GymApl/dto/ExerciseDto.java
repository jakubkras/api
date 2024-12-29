package com.GymApl.dto;

import com.GymApl.Entity.SetDetails;
import com.GymApl.Entity.Training;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto {

    private String name;

    List<SetDetailsDto> setDetails = new ArrayList<>();

}
