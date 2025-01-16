package com.GymApl.Controller;
import com.GymApl.Entity.Training;
import com.GymApl.Repository.TrainingRepository;
import com.GymApl.Service.ExerciseService;
import com.GymApl.dto.ExerciseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exercise")
@AllArgsConstructor
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    TrainingRepository trainingRepository;

    @PostMapping
    ResponseEntity<String> createExercise (@RequestParam String exerciseName,
                                           @RequestParam String typeOfWorkout) {
        Optional<Training> trainingOpt = trainingRepository.findByTypeOfWorkout(typeOfWorkout);
        if (trainingOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nie znaleziono takiego treningu");
        Training training = trainingOpt.get();
        exerciseService.createExercise(exerciseName, training);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pomyslnie dodano cwiczenie do treningu: "+ typeOfWorkout);
    }

    @GetMapping
    ResponseEntity<List<ExerciseDto>> findAllExercises (@RequestParam String typeOfWorkout){
        List<ExerciseDto> exercises = exerciseService.findAllExercises(typeOfWorkout);
        return ResponseEntity.ok(exercises);
    }

    @DeleteMapping
    ResponseEntity<String> deleteExercise(@RequestParam String exerciseName,
                                          @RequestParam String typeOfWorkout){
        exerciseService.deleteByName(exerciseName, typeOfWorkout);
        return ResponseEntity.ok("Pomyslnie usunieto cwiczenie: "+ exerciseName);
    }
}