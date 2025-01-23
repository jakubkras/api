package com.GymApl.Service;


import com.GymApl.Entity.Exercise;
import com.GymApl.Entity.Training;
import com.GymApl.Repository.ExerciseRepository;
import com.GymApl.Repository.TrainingRepository;
import com.GymApl.Security.UserDetailsImplementation;
import com.GymApl.dto.ExerciseDto;
import com.GymApl.dto.SetDetailsDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Getter
public class ExerciseService {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    TrainingRepository trainingRepository;

    public Exercise createExercise (String exerciseName, Training training){
        Optional<Exercise> exerciseOpt = exerciseRepository.findByNameAndTraining_TypeOfWorkoutAndTraining_UserID_Id(exerciseName, training.getTypeOfWorkout(),UUID.fromString(authentication()));

        if (exerciseOpt.isPresent())
            throw new RuntimeException("Cwiczenie o takiej nazwie: "+ exerciseName +" już istnieje w treningu "+ training.getTypeOfWorkout());


        if (exerciseOpt.isEmpty()){
            Exercise exercise = new Exercise();
            exercise.setName(exerciseName);
            exercise.setId(exercise.getId());
            exercise.setTypeOfWorkout(training.getTypeOfWorkout());
            exercise.setTraining(training);
            exerciseRepository.save(exercise);
            return exercise;
        }
        return exerciseOpt.get();
    }

    public List<Exercise> createExercises(List<String> exerciseNames, Training training){
        List<Exercise> exercises = new ArrayList<>();
        for (String exerciseName : exerciseNames){
            exercises.add(createExercise(exerciseName,training));
        }
        return  exercises;
    }

    public List<ExerciseDto> findAllExercises (String typeOfWorkout){


        Optional <Training> trainingOpt = trainingRepository.findByTypeOfWorkoutAndUserID_Id(typeOfWorkout, UUID.fromString(authentication()));
        if (trainingOpt.isEmpty()){
            throw new RuntimeException("Nie ma treningu o nazwie:" + typeOfWorkout);
        }
            Training training = trainingOpt.get();

       List<Exercise> exercises = exerciseRepository.findByTrainingId(training.getId());
       return exercises.stream().map(exercise -> {
           ExerciseDto exerciseDto = new ExerciseDto();
           exerciseDto.setName(exercise.getName());
           exerciseDto.setSetDetails(exercise.getSetDetails().stream().map
                   (setDetails -> {
                       SetDetailsDto setDetailsDto = new SetDetailsDto();
                       setDetailsDto.setReps(setDetails.getReps());
                       setDetailsDto.setWeight(setDetails.getWeight());
                       return setDetailsDto;
                   }).collect(Collectors.toList())
           );
           return exerciseDto;
       })
               .collect(Collectors.toList());
    }

    @Transactional
    public void deleteByName (String exerciseName, String typeOfWorkout){
       if (!trainingRepository.existsByTypeOfWorkoutAndUserID_Id(typeOfWorkout, UUID.fromString(authentication()))){
           throw new IllegalArgumentException("Nie ma takiego treningu: " +typeOfWorkout);
       }

        if (!exerciseRepository.existsByNameAndTraining_UserID_Id(exerciseName, UUID.fromString(authentication()))){
           throw new IllegalArgumentException("Nie ma ćwiczenia "+exerciseName+ " przypisanego do treningu: " +typeOfWorkout);
       }

        exerciseRepository.deleteByNameAndTraining_UserID_Id(exerciseName, UUID.fromString(authentication()));
    }

    public String authentication(){
        String userId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null && authentication.getPrincipal() instanceof UserDetailsImplementation){
            UserDetailsImplementation userDetailsImplementation = (UserDetailsImplementation) authentication.getPrincipal();
            userId = userDetailsImplementation.getId().toString();
        }else {
            throw new IllegalArgumentException("Brak zalogowanego użytkownika");
        }
        return userId;
    }
}

