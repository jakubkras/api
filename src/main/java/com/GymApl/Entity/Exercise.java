package com.GymApl.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@Entity
@Table(name = "exercise")
public class Exercise {

        @Id
        @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

        @Column(name= "name")
    private String name;

    @OneToMany (mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SetDetails> setDetails = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;
}
