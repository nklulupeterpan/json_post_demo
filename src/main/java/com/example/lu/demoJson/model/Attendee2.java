package com.example.lu.demoJson.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Attendee2 {

    private Integer attendeeCount;
    private List<String> attendees;
    private String name;
    private Date startDate;
}
