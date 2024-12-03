package com.GymApl.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
@Entity
@Table(name = "excercise")
public class Excercise {

        @Id
        @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private int load;
    private int reps;
    private int sets;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;
}
