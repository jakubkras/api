package com.GymApl.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name="training")
public class Training {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int trainingNumber;

@Column(nullable = false)
    private Integer lastTrainingNumber =0;

    @Column(nullable = false)
    private LocalDateTime date;
    private String typeOfWorkout;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users userID;

    @OneToMany (mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();

@PrePersist
    public  void prePersist(){
        this.trainingNumber = lastTrainingNumber + 1;
        lastTrainingNumber = this.trainingNumber;
    }

}