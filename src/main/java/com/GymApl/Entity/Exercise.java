package com.GymApl.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
<<<<<<< HEAD
import lombok.NoArgsConstructor;
=======
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
<<<<<<< HEAD
@NoArgsConstructor
=======
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5
@Entity
@Table(name = "exercise")
public class Exercise {

        @Id
        @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

        @Column(name= "name")
    private String name;

<<<<<<< HEAD
        @Column(name = "typeOfWorkout")
        private String typeOfWorkout;

    @OneToMany (mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SetDetails> setDetails = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
=======
    @OneToMany (mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SetDetails> setDetails = new ArrayList<>();

    @ManyToOne
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;
}
