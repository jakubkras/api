package com.GymApl.Controller;

import com.GymApl.Entity.Exercise;
import com.GymApl.Entity.SetDetails;
import com.GymApl.Entity.Training;
import com.GymApl.Service.TrainingService;
import com.GymApl.dto.CreateTrainingRequest;
import com.GymApl.dto.ExerciseDto;
import com.GymApl.dto.SetDetailsDto;
import com.GymApl.dto.TrainingDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/trainings")
public class TrainingController {
    @Autowired
    TrainingService trainingService;

    @PostMapping
    public ResponseEntity<String> createTraining(@RequestBody CreateTrainingRequest request) {
        System.out.println("Type of Workout: " + request.getTypeOfWorkout());
        System.out.println("Exercise Names: " + request.getSetDetailsList());
        System.out.println("Set Details: " + request.getExerciseNames());
        trainingService.createTraining(request.getTypeOfWorkout(), request.getSetDetailsList(), request.getExerciseNames());
        return ResponseEntity.status(HttpStatus.CREATED).body("Dodano pomyślnie trening");
    }

    @GetMapping
    public ResponseEntity<List<TrainingDto>> findAll() {
        List<TrainingDto> trainings = trainingService.findAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTraining(@RequestParam String typeOfWorkout) {
        trainingService.deleteTraining(typeOfWorkout);
        return ResponseEntity.ok("Pomyślnie usunięto trening o nazwie: " + typeOfWorkout);
    }
}
