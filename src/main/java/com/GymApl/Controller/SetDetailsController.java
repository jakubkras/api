package com.GymApl.Controller;

import com.GymApl.Entity.SetDetails;
import com.GymApl.Service.SetDetailsService;
import com.GymApl.dto.SetDetailsDto;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/set-details")
@AllArgsConstructor
public class SetDetailsController {

    @Autowired
    SetDetailsService setDetailsService;

    @PostMapping
    ResponseEntity<String> createSetDetails (@RequestParam String typeOfWorkout,
                                             @RequestParam String exerciseName,
                                             @RequestBody List<SetDetailsDto> setDetailsDtoList){

      List<SetDetails> setDetailsList = setDetailsService.createSetDetails(typeOfWorkout, exerciseName, setDetailsDtoList);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Pomyślnie dodano " + setDetailsList.size() + " zestawów do ćwiczenia: " +
                        exerciseName + " w treningu: " + typeOfWorkout);
    }

    @GetMapping
    ResponseEntity<List<SetDetailsDto>> findAllSetDetails(@RequestParam String typeOfWorkout,
                                                          @RequestParam String exerciseName){
        List<SetDetailsDto> setDetailsDtoList = setDetailsService.findAllSetDetails(typeOfWorkout, exerciseName);
        return  ResponseEntity.ok(setDetailsDtoList);
    }

    @DeleteMapping
    ResponseEntity<String> deleteSetDetails(@RequestParam String typeOfWorkout,
                                            @RequestParam String exerciseName){

        setDetailsService.deleteSetDetail(typeOfWorkout, exerciseName);
       return ResponseEntity.ok("Pomyslnie usunieto szczegóły serii dla treningu: "+typeOfWorkout+ " i ćwiczenia: "+exerciseName);
    }
}
