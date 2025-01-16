package com.GymApl.Repository;

import com.GymApl.Entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Optional<Exercise> findByNameAndTraining_UserID_Id (String name, UUID userId);
    Optional<Exercise> findByNameAndTraining_TypeOfWorkoutAndTraining_UserID_Id (String name,String typeOfWorkout , UUID userId);

    void deleteByNameAndTraining_UserID_Id (String exerciseName, UUID userId);
    boolean existsByNameAndTraining_UserID_Id (String exerciseName, UUID userId);
    List<Exercise> findByTrainingId (Integer trainingId);

    Optional<Exercise> findByName (String name);


}
