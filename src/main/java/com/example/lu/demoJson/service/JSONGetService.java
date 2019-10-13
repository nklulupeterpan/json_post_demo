package com.example.lu.demoJson.service;

import com.example.lu.demoJson.model.Record;
import com.example.lu.demoJson.model.Result;
import com.example.lu.demoJson.model.ResultWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class JSONGetService {

    public static final String GET_URL = "http://jsonplaceholder.typicode.com/todos";

    public  List<Record> getJson() {
        String inline = "";
        List<Record> recordList = new ArrayList<>();
        try {
            URL url = new URL(GET_URL);
            //Parse URL into HttpURLConnection in order to open the connection in order to get the JSON data
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Set the request to GET or POST as per the requirements
            conn.setRequestMethod("GET");
            //Use the connect method to create the connection bridge
            conn.connect();
            //Get the response status of the Rest API
            int responsecode = conn.getResponseCode();
            System.out.println("Response code is: " + responsecode);

            //Iterating condition to if response code is not 200 then throw a runtime exception
            //else continue the actual process of getting the JSON data
            if (responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else {
                //Scanner functionality will read the JSON data from the stream
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                System.out.println("\nJSON Response in String format");
                System.out.println(inline);
                //Close the stream when reading the data has been finished
                sc.close();
            }
            ObjectMapper objectMapper = new ObjectMapper();

            String temp = "{\"result\":{\"label\": \"labeltest\",\"recordList\":[{\"userId\":2,\"id\": 1, \"title\":\"hahaha\",\"completed\":false }, {\"userId\":2,\"id\": 1, \"title\":\"hajdhaha\",\"completed\":false }]}}";

            ResultWrapper result = objectMapper.readValue(temp,  ResultWrapper.class);

            recordList = result.getResult().getRecordList();

//            JSONParser parser = new JSONParser();
//            JSONArray array = (JSONArray) parser.parse(inline);
//
//
//            // Loop through each item
//            array.forEach(o -> {
//                JSONObject record = (JSONObject) o;
//
//                Long userId = (Long) record.get("userId");
//                System.out.println("USER ID : " + userId);
//
//                Long id = (Long) record.get("id");
//                System.out.println("ID : " + id);
//
//                String title = (String) record.get("title");
//                System.out.println("Title : " + title);
//
//                Boolean completed = (Boolean) record.get("completed");
//                System.out.println("completed : " + completed);
//
//
//                System.out.println("\n");
//            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recordList;
    }

    private HttpURLConnection createConnection(String urlString) throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL(String.format(urlString));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

}
