package com.example.lu.demoJson.service;

import com.example.lu.demoJson.model.Record;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class JSONPostService {

    public void postJSON(List<Record> recordList) {

        if (recordList != null && recordList.size() > 0) {
            try {
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost("http://httpbin.org/post");


                ObjectMapper objectMapper = new ObjectMapper();
                String recordListString = objectMapper.writeValueAsString(recordList);
                String json = "{\"name\": \"Upendra\", \"job\": \"Programmer\"}";

                StringEntity entity = new StringEntity(recordListString);
                httpPost.setEntity(entity);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                CloseableHttpResponse response = client.execute(httpPost);

                if (response.getStatusLine().getStatusCode() == 200) {
                    String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                    System.out.println(responseString);
                }
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//                URL url = new URL("https://postman-echo.com/post");
//
//                HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                con.setRequestMethod("POST");
//                con.setRequestProperty("Content-Type", "application/json");
//                con.setRequestProperty("Accept", "application/json");
//                con.setDoOutput(true);
//
//                ObjectMapper objectMapper = new ObjectMapper();
//
//                String recordListString = objectMapper.writeValueAsString(recordList);
//
//                String jsonInputString = "{\"name\": \"Upendra\", \"job\": \"Programmer\"}";
//                OutputStream os = con.getOutputStream();
//                os.write(jsonInputString.getBytes());
//                os.flush();
//
//                if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
//                    throw new RuntimeException("Failed : HTTP error code : "
//                            + con.getResponseCode());
//                }
//
//                BufferedReader br = new BufferedReader(new InputStreamReader(
//                        (con.getInputStream())));
//
//                String output;
//                System.out.println("Output from Server .... \n");
//                while ((output = br.readLine()) != null) {
//                    System.out.println(output);
//                }
//
//                con.disconnect();
//
//            } catch (MalformedURLException e) {
//
//                e.printStackTrace();
//
//            } catch (IOException e) {
//
//                e.printStackTrace();
//
//            }

        }
    }
}
