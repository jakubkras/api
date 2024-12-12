package com.GymApl.Repository;

import com.GymApl.Entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface TrainingRepository extends JpaRepository <Training, Integer> {
    Training findByDate (LocalDateTime date);
    List<Training> findByTypeOfWorkout (String TypeOfWorkout);
    List<Training> findByUserID_Id(UUID uuid);

    @Query ("SELECT t FROM Training t WHERE t.userID.id = :userId ORDER BY t.trainingNumber DESC")
    List<Training> findLastestTraining (@Param("userId")UUID id);


}
