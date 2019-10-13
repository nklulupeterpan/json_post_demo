package com.example.lu.demoJson.service;

import com.example.lu.demoJson.model.Partner;
import com.example.lu.demoJson.model.Partners;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class JSONGetService {

    public static final String GET_URL = "https://candidate.hubteam.com/candidateTest/v3/problem/dataset?userKey=3b4898064fd14cd139e44e5e9c82";

    public  List<Partner> getJson() {
        String inline = "";
        List<Partner> partnerList = new ArrayList<>();
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
             Partners partners = objectMapper.readValue(inline,  Partners.class);

            partnerList = partners.getPartners();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return partnerList;
    }

    private HttpURLConnection createConnection(String urlString) throws MalformedURLException, IOException, ProtocolException {
        URL url = new URL(String.format(urlString));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

}
