package com.example.myplane.models;

import android.net.Uri;
import android.util.Log;

import com.example.myplane.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class HttpController {
    public HttpController() {
    }

    public String call(String requestUrl,String apiKey,String company) throws MalformedURLException {
        if(!company.isEmpty()){
            StringBuilder companyParam = new StringBuilder();
            companyParam
                    .append("?")
                    .append("name")
                    .append("=")
                    .append(company);
            requestUrl += companyParam.toString();
        }
        URL url = new URL(requestUrl);
        String response = "";
        try{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-api-key",apiKey);
            InputStream in = new BufferedInputStream(connection.getInputStream());
            response = convertStreamToJsonString(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String call(String requestUrl,String apiKey,String manufacturer, String model) throws MalformedURLException {
        StringBuilder parameters = new StringBuilder();
        parameters.append("?");
        StringBuilder manufacturerParam = new StringBuilder();
        if(!manufacturer.isEmpty()){
            manufacturerParam.append("manufacturer")
                    .append("=")
                    .append(manufacturer);
        }
        StringBuilder modelParam = new StringBuilder();
        if(!model.isEmpty()){
            modelParam.append("model")
                    .append("=")
                    .append(model);
        }
        if(!manufacturer.isEmpty()&&model.isEmpty()){
            parameters.append(manufacturerParam)
                    .append("&")
                    .append("limit=")
                    .append(30);
        }
        else if(manufacturer.isEmpty()&&!model.isEmpty()){
            parameters.append(modelParam)
                    .append("&")
                    .append("limit=")
                    .append(30);
        }
        else if(!manufacturer.isEmpty()&&!model.isEmpty()){
            parameters.append(manufacturerParam)
                    .append("&")
                    .append(modelParam);
        }
        Log.i("INFO",parameters.toString());
        requestUrl += parameters.toString();

        URL url = new URL(requestUrl);
        String response = "";
        try{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-api-key",apiKey);
            InputStream in = new BufferedInputStream(connection.getInputStream());
            response = convertStreamToJsonString(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String convertStreamToJsonString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
