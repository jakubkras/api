package com.GymApl.dto;


import lombok.Data;

import java.util.List;

@Data
public class CreateTrainingRequest {

    private String typeOfWorkout;
    private List<String> exerciseNames;
    private List<List<SetDetailsDto>> setDetailsList;
}
