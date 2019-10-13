package com.example.lu.demoJson.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Result {
   private List<Record> recordList;
   private String label;
}
