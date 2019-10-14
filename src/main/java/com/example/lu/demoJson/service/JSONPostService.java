package com.example.lu.demoJson.service;

import com.example.lu.demoJson.model.Countries;
import com.example.lu.demoJson.model.Partner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class JSONPostService {
    private static final Logger logger = LoggerFactory.getLogger(JSONGetService.class);

    public boolean postJSON(Countries countries) {
        boolean successful = false;
        if (countries != null) {
            try {
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost("https://candidate.hubteam.com/candidateTest/v3/problem/result?userKey=3b4898064fd14cd139e44e5e9c82");


                ObjectMapper objectMapper = new ObjectMapper();
                String recordListString = objectMapper.writeValueAsString(countries);

                StringEntity entity = new StringEntity(recordListString);
                httpPost.setEntity(entity);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                CloseableHttpResponse response = client.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    successful = true;
                    logger.info("POST is succesful");
                }

                String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("POST response : {}", responseString);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return successful;
    }
}
