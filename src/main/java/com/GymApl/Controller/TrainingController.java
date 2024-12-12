package com.GymApl.Controller;

import com.GymApl.Entity.Exercise;
import com.GymApl.Entity.Training;
import com.GymApl.Service.TrainingService;
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
    public ResponseEntity<String> createTraining(@RequestBody TrainingDto trainingDto,
                                         @RequestParam(required = false)List<Exercise> exercises){
        trainingService.createTraining(trainingDto, exercises);
        return ResponseEntity.status(HttpStatus.CREATED).body("Dodano pomyslnie trening");

    }

    @GetMapping ("/all")
    public ResponseEntity<List<TrainingDto>> findAll (){

        List<TrainingDto> trainings= trainingService.findAllTrainings();
        return ResponseEntity.ok(trainings);
    }



}
