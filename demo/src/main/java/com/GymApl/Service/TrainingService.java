package com.GymApl.Service;

import com.GymApl.Entity.Exercise;
import com.GymApl.Entity.SetDetails;
import com.GymApl.Entity.Training;
import com.GymApl.Entity.Users;
import com.GymApl.Repository.ExerciseRepository;
import com.GymApl.Repository.SetDetailsRepository;
import com.GymApl.Repository.TrainingRepository;
import com.GymApl.Repository.UserRepositoryDefault;
import com.GymApl.Security.UserDetailsImplementation;
import com.GymApl.dto.ExerciseDto;
import com.GymApl.dto.SetDetailsDto;
import com.GymApl.dto.TrainingDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainingService {

    @Autowired
    private final TrainingRepository trainingRepository;

    @Autowired
    private final UserRepositoryDefault userRepository;

    @Autowired
    private SetDetailsRepository setDetailsRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseService exerciseService;

    public Training createTraining(String typeOfWorkout, List<List<SetDetailsDto>> setDetailsList, List<String> exerciseNames) {
        if (typeOfWorkout == null) {
            throw new IllegalArgumentException("Pole typ treningu nie może być puste.");
        }

        Optional<Training> trainingOpt = trainingRepository.findByTypeOfWorkout(typeOfWorkout);
        if (trainingOpt.isPresent()) {
            throw new RuntimeException("Nazwa treningu musi być unikalna");
        }

        if (exerciseNames == null) {
            throw new IllegalArgumentException("Lista nazw ćwiczeń nie może być pusta.");
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsImplementation) {
            UserDetailsImplementation userDetails = (UserDetailsImplementation) principal;
            String currentUser = userDetails.getId().toString();

            Optional<Users> userOptional = userRepository.findById(UUID.fromString(currentUser));
            if (userOptional.isEmpty()) {
                throw new IllegalArgumentException("Nie znaleziono użytkownika o takim id: " + currentUser);
            }

            Users user = userOptional.get();

            Training training = new Training();
            Training lastTraining = trainingRepository.findTopByUserIDOrderByTrainingNumberDesc(user);
            training.setLastTrainingNumber(lastTraining != null ? lastTraining.getTrainingNumber() : 0);
            training.setTrainingNumber(training.getLastTrainingNumber() + 1);
            training.setDate(LocalDateTime.now().withNano(0).withSecond(0));
            training.setTypeOfWorkout(typeOfWorkout);
            training.setUserID(user);

            trainingRepository.save(training);

            List<Exercise> exercisesList = new ArrayList<>();
            for (String exerciseName : exerciseNames) {
                Exercise exercise = new Exercise();
                exercise.setName(exerciseName);
                exercise.setTraining(training);
                exercise.setTypeOfWorkout(training.getTypeOfWorkout());
                exercisesList.add(exercise);
            }

            exerciseRepository.saveAll(exercisesList);

            for (int i = 0; i < exerciseNames.size(); i++) {
                Exercise exercise = exercisesList.get(i);
                List<SetDetailsDto> setDetailsDtoList = setDetailsList.get(i);
                for (SetDetailsDto setDetailsDto : setDetailsDtoList) {
                    SetDetails setDetails = new SetDetails();
                    setDetails.setWeight(setDetailsDto.getWeight());
                    setDetails.setReps(setDetailsDto.getReps());
                    setDetails.setTraining(training);
                    setDetails.setExercise(exercise);
                    setDetailsRepository.save(setDetails);
                }
            }

            return training;
        } else {
            throw new IllegalStateException("Brak zalogowanego użytkownika.");
        }
    }

    public List<TrainingDto> findAllTrainings() {
        String userID;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImplementation) {
            UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();
            userID = userDetails.getId().toString();
        } else {
            throw new IllegalArgumentException("Brak zalogowanego użytkownika.");
        }

        List<Training> trainings = trainingRepository.findByUserID_Id(UUID.fromString(userID));

        return trainings.stream().map(training -> {
            List<ExerciseDto> exerciseDtos = training.getExercises().stream().map(exercise -> {
                List<SetDetailsDto> setDetailsDtoList = exercise.getSetDetails().stream().map(setDetails ->
                        new SetDetailsDto(setDetails.getWeight(), setDetails.getReps())).collect(Collectors.toList());
                return new ExerciseDto(exercise.getName(), setDetailsDtoList);
            }).collect(Collectors.toList());
            return new TrainingDto(training.getTypeOfWorkout(), training.getTrainingNumber(),
                    training.getDate(), training.getUserID().getId(), exerciseDtos);
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteTraining(String typeOfWorkout) {
        String userId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImplementation) {
            UserDetailsImplementation userDetailsImplementation = (UserDetailsImplementation) authentication.getPrincipal();
            userId = userDetailsImplementation.getId().toString();
        } else {
            throw new IllegalArgumentException("Brak zalogowanego użytkownika");
        }

        Optional<Training> trainingOpt = trainingRepository.findByTypeOfWorkoutAndUserID_Id(typeOfWorkout, UUID.fromString(userId));
        if (trainingOpt.isEmpty()) {
            throw new IllegalArgumentException("Trening o nazwie: " + typeOfWorkout + " nie istnieje lub nie należy do ciebie");
        }

        trainingRepository.delete(trainingOpt.get());
    }
}
