package com.GymApl.dto;


import com.GymApl.Entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5
import java.util.UUID;

@Data
@AllArgsConstructor
<<<<<<< HEAD
=======
@Setter
@Getter
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5
public class TrainingDto {

    private String typeOfWorkout;

    private int trainingNumber;

    private LocalDateTime date;

    private UUID userID;

<<<<<<< HEAD
    private List<ExerciseDto> exercises;

=======
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5

}
