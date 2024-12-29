package com.GymApl.Service;


import com.GymApl.Entity.Exercise;
import com.GymApl.Entity.SetDetails;
import com.GymApl.Entity.Training;
import com.GymApl.Repository.ExerciseRepository;
import com.GymApl.Repository.SetDetailsRepository;
import com.GymApl.Repository.TrainingRepository;
import com.GymApl.Security.UserDetailsImplementation;
import com.GymApl.dto.SetDetailsDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SetDetailsService {

    @Autowired
    SetDetailsRepository setDetailsRepository;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    public List<SetDetails> createSetDetails(String typeOfWorkout, String exerciseName, List<SetDetailsDto> setDetailsDtoList) {

        Optional<Training> trainingOpt = trainingRepository.findByTypeOfWorkoutAndUserID_Id(String.valueOf(typeOfWorkout), UUID.fromString(authentication()));

        if (trainingOpt.isEmpty()) {
            throw new RuntimeException("Nie znaleziono takiego treningu");
        }

        Training training = trainingOpt.get();

        Optional<Exercise> exerciseOpt = exerciseRepository.findByNameAndTraining_TypeOfWorkoutAndTraining_UserID_Id(String.valueOf(exerciseName),typeOfWorkout, UUID.fromString(authentication()));

        if (exerciseOpt.isEmpty()) {
            throw new RuntimeException("Nie znaleziono takiego ćwicznia w treningu: " + typeOfWorkout);
        }

        Exercise exercise = exerciseOpt.get();

        List<SetDetails> setDetailsList = setDetailsDtoList.stream().map(dto-> {

            SetDetails setDetails = new SetDetails();
            setDetails.setExercise(exercise);
            setDetails.setReps(dto.getReps());
            setDetails.setWeight(dto.getWeight());
            setDetails.setTraining(training);
            return setDetails;
        }).toList();

        return setDetailsRepository.saveAll(setDetailsList);
    }

    public List<SetDetailsDto> findAllSetDetails(String typeOfWorkout, String exerciseName){

        Optional<Training> trainingOptional = trainingRepository.findByTypeOfWorkoutAndUserID_Id(typeOfWorkout, UUID.fromString(authentication()));

        if (trainingOptional.isEmpty()){
            throw new RuntimeException("Taki trening: " +typeOfWorkout + " nie istnieje");
        }

        Optional<Exercise> exerciseOptional = exerciseRepository.findByNameAndTraining_TypeOfWorkoutAndTraining_UserID_Id(exerciseName, typeOfWorkout, UUID.fromString(authentication()));

        if (exerciseOptional.isEmpty()){
            throw new RuntimeException("Takie ćwiczenie: " +exerciseName + " nie istnieje");
        }
        Exercise exercise = exerciseOptional.get();

        List<SetDetails> setDetails = setDetailsRepository.findByExerciseIdAndTraining_UserID_Id(exercise.getId(), UUID.fromString(authentication()));
return setDetails.stream().map(setDetail ->{
    SetDetailsDto setDetailsDto = new SetDetailsDto();
    setDetailsDto.setReps(setDetail.getReps());
    setDetailsDto.setWeight(setDetail.getWeight());
    return setDetailsDto;
}).collect(Collectors.toList());
    }

    @Transactional
    public String deleteSetDetail(String typeOfWorkout, String exerciseName){

       if(!trainingRepository.existsByTypeOfWorkoutAndUserID_Id(typeOfWorkout, UUID.fromString(authentication()))){
           throw new IllegalArgumentException("Taki trening: "+typeOfWorkout + " nie istnieje");
       }

       Optional<Exercise> exerciseOpt = exerciseRepository.findByNameAndTraining_TypeOfWorkoutAndTraining_UserID_Id(exerciseName, typeOfWorkout, UUID.fromString(authentication()));
        if (exerciseOpt.isEmpty()){
            throw new IllegalArgumentException("Nie ma ćwiczenia "+exerciseName+ " przypisanego do treningu: " +typeOfWorkout);
        }
        int exerciseId = exerciseOpt.get().getId();

        setDetailsRepository.deleteByExercise_IdAndTraining_UserID_Id(exerciseId, UUID.fromString(authentication()));
    return "Pomyslnie usunieto szczegóły serii dla treningu: "+ typeOfWorkout + " i ćwiczenia: "+exerciseName;
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
