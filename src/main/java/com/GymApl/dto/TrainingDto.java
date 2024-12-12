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
import java.util.UUID;

@Data
@AllArgsConstructor
@Setter
@Getter
public class TrainingDto {

    private String typeOfWorkout;

    private int trainingNumber;

    private LocalDateTime date;

    private UUID userID;


}
