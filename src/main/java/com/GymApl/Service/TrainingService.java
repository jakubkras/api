package com.GymApl.Service;

import com.GymApl.Entity.Exercise;
import com.GymApl.Entity.Training;
import com.GymApl.Entity.Users;
import com.GymApl.Repository.TrainingRepository;
import com.GymApl.Repository.UserRepositoryDefault;
import com.GymApl.Security.UserDetailsImplementation;
import com.GymApl.dto.TrainingDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Training createTraining(TrainingDto trainingDto, List<Exercise> exercises) {


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsImplementation) {
            UserDetailsImplementation userDetails = (UserDetailsImplementation) principal;

            String currentUser = userDetails.getId().toString();

            Optional<Users> userOptional = userRepository.findById(UUID.fromString(currentUser));

            if (userOptional.isEmpty()) {
                throw new IllegalArgumentException("Nie znaleziono użytkownika o takim id: " + currentUser);
            }

            List<Training> lastTrainings = trainingRepository.findLastestTraining(UUID.fromString(currentUser));
            Training lastTraining = lastTrainings.isEmpty() ? null : lastTrainings.get(0);
            Users user = userOptional.get();

            Training training = new Training();

            if (lastTraining != null) {
                training.setLastTrainingNumber(lastTraining.getTrainingNumber());
            } else {
                training.setLastTrainingNumber(0);
            }

            training.setTrainingNumber(training.getTrainingNumber());
            training.setDate(LocalDateTime.now().withNano(0).withSecond(0));
            training.setTypeOfWorkout(trainingDto.getTypeOfWorkout());
            training.setUserID(user);

            if (exercises != null) {
                training.setExercises(exercises);
            }

            return trainingRepository.save(training);
        } else {
            throw new IllegalStateException("Brak zalogowanego użytkownika.");
        }
    }

    public List<TrainingDto> findAllTrainings (){
        String userID;
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       if (authentication !=null && authentication.getPrincipal() instanceof UserDetailsImplementation) {
           UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();
           userID = userDetails.getId().toString();
       } else {
           throw new  IllegalArgumentException ("Brak zalogowanego użytkownika");
       }

        List<Training> trainings = trainingRepository.findByUserID_Id(UUID.fromString(userID));

   return trainings.stream().map(training -> new TrainingDto(training.getTypeOfWorkout(),
                training.getTrainingNumber(), training.getDate(), training.getUserID().getId())).collect(Collectors.toList());

    }



}
