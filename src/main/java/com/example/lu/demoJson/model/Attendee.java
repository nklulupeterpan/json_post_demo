package com.example.lu.demoJson.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Attendee {

    private Integer attendeeCount;
    private List<String> attendees = new ArrayList<>();
    private String name;
    private String startDate;
}
