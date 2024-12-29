package com.GymApl.Repository;

import com.GymApl.Entity.SetDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SetDetailsRepository extends JpaRepository<SetDetails, Integer> {

    List<SetDetails> findByExerciseIdAndTraining_UserID_Id(Integer exerciseId, UUID userId);

    void deleteByExercise_IdAndTraining_UserID_Id(int exerciseId, UUID userId);
}
