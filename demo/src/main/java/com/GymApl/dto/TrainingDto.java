package com.GymApl.dto;


import com.GymApl.Entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDto {



    private String typeOfWorkout;

    private int trainingNumber;

    private LocalDateTime date;

    private UUID userID;

    private List<ExerciseDto> exercises;

}
