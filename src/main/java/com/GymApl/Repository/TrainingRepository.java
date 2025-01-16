package com.GymApl.Repository;

import com.GymApl.Entity.Training;
import com.GymApl.Entity.Users;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {

    Training findByDate (LocalDateTime date);
    Optional<Training> findByTypeOfWorkout (String TypeOfWorkout);
    List<Training> findByUserID_Id(UUID uuid);
    boolean existsByTypeOfWorkoutAndUserID_Id (String typeOfWorkout, UUID userID);
    Optional<Training> findByTypeOfWorkoutAndUserID_Id(String typeOfWorkout, UUID UserId);

    @Query ("SELECT t FROM Training t WHERE t.userID.id = :userId ORDER BY t.trainingNumber DESC")
    List<Training> findLastestTraining (@Param("userId")UUID id);


    Training findTopByUserIDOrderByTrainingNumberDesc(Users user);
}