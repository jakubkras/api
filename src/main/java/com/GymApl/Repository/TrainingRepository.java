package com.GymApl.Repository;

import com.GymApl.Entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
<<<<<<< HEAD

=======
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
<<<<<<< HEAD

@Repository
public interface TrainingRepository extends JpaRepository <Training, Integer> {
    Optional<Training> findByTypeOfWorkout (String TypeOfWorkout);
    List<Training> findByUserID_Id(UUID uuid);
    boolean existsByTypeOfWorkoutAndUserID_Id (String typeOfWorkout, UUID userID);
    Optional<Training> findByTypeOfWorkoutAndUserID_Id(String typeOfWorkout, UUID UserId);
=======
import java.util.stream.Stream;

@Repository
public interface TrainingRepository extends JpaRepository <Training, Integer> {
    Training findByDate (LocalDateTime date);
    List<Training> findByTypeOfWorkout (String TypeOfWorkout);
    List<Training> findByUserID_Id(UUID uuid);
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5

    @Query ("SELECT t FROM Training t WHERE t.userID.id = :userId ORDER BY t.trainingNumber DESC")
    List<Training> findLastestTraining (@Param("userId")UUID id);


<<<<<<< HEAD

=======
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5
}
