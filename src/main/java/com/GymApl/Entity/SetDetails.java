package com.GymApl.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
<<<<<<< HEAD
import lombok.NoArgsConstructor;
=======
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5

@Data
@Entity
@AllArgsConstructor
<<<<<<< HEAD
@NoArgsConstructor
=======
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5
@Table(name = "set_details")
public class SetDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "weight")
    private double weight;

<<<<<<< HEAD
=======
    @Column(name = "set_number")
    private int setNumber;

>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5
    @Column(name= "reps")
    private int reps;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

=======
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5
}
