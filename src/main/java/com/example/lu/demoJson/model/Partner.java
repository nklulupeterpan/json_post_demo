package com.example.lu.demoJson.model;

import com.example.lu.demoJson.service.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Partner {
    private String firstName;
    private String lastName;
    private String email;
    private String country;

    @JsonSerialize(contentUsing = JsonDateSerializer.class)
    private List<Date> availableDates;

}
