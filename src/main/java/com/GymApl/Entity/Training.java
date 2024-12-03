package com.GymApl.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name="training")
public class Training {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;
    private String typeOfWorkout;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToMany (mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Excercise> excercises = new ArrayList<>();

}
