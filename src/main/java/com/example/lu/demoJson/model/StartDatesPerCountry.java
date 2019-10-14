package com.example.lu.demoJson.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class StartDatesPerCountry {

    private String name;
    private List<String> startDates;
}
