package com.example.lu.demoJson.controller;

import com.example.lu.demoJson.model.Partner;
import com.example.lu.demoJson.service.CalculateAttend;
import com.example.lu.demoJson.service.JSONGetService;
import com.example.lu.demoJson.service.JSONPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostJSON {


    @Autowired
    private JSONGetService getService;
    @Autowired
    private JSONPostService postService;
    @Autowired
    private CalculateAttend calculateAttend;

    @RequestMapping(value = "/processJson")
    public ResponseEntity<Object> getAllEmployees()
    {

        List<Partner> partnerList = getService.getJson();


        postService.postJSON(calculateAttend.doCalculate(partnerList));
        return new ResponseEntity<Object>(partnerList, HttpStatus.OK);

    }

}
