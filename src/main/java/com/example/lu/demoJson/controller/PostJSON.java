package com.example.lu.demoJson.controller;

import com.example.lu.demoJson.model.Record;
import com.example.lu.demoJson.service.JSONGetService;
import com.example.lu.demoJson.service.JSONPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PostJSON {


    @Autowired
    private JSONGetService getService;
    @Autowired
    private JSONPostService postService;

    @RequestMapping(value = "/processJson")
    public ResponseEntity<Object> getAllEmployees()
    {

        List<Record> recordList = getService.getJson();

        postService.postJSON(recordList);
        return new ResponseEntity<Object>(recordList, HttpStatus.OK);

    }

}
