package com.GymApl.Controller;

import com.GymApl.Entity.Exercise;
<<<<<<< HEAD
import com.GymApl.Entity.SetDetails;
import com.GymApl.Entity.Training;
import com.GymApl.Service.TrainingService;
import com.GymApl.dto.CreateTrainingRequest;
import com.GymApl.dto.SetDetailsDto;
=======
import com.GymApl.Entity.Training;
import com.GymApl.Service.TrainingService;
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5
import com.GymApl.dto.TrainingDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users/trainings")
public class TrainingController {

    @Autowired
    TrainingService trainingService;

    @PostMapping("/create")
<<<<<<< HEAD
    public ResponseEntity<String> createTraining(@RequestBody CreateTrainingRequest request) {
        trainingService.createTraining(request.getTypeOfWorkout(), request.getSetDetailsList(), request.getExerciseNames());
=======
    public ResponseEntity<String> createTraining(@RequestBody TrainingDto trainingDto,
                                         @RequestParam(required = false)List<Exercise> exercises){
        trainingService.createTraining(trainingDto, exercises);
>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5
        return ResponseEntity.status(HttpStatus.CREATED).body("Dodano pomyslnie trening");

    }

    @GetMapping ("/all")
    public ResponseEntity<List<TrainingDto>> findAll (){

        List<TrainingDto> trainings= trainingService.findAllTrainings();
        return ResponseEntity.ok(trainings);
    }
<<<<<<< HEAD
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTraining (@RequestParam String typeOfWorkout){
       trainingService.deleteTraining(typeOfWorkout);
        return  ResponseEntity.ok("Pomyslnie usunieto trening o nazwie: "+typeOfWorkout);
    }
=======

>>>>>>> 60e89f6e638fe239bb7480a1d9640befd274e2f5


}
