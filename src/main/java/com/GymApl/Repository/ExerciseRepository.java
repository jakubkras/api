package com.GymApl.Repository;

import com.GymApl.Entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Exercise findByName (String name);
    void deleteByName (String name);

}
