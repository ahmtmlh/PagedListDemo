package com.example.pagedlistdemo.dao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import static com.example.pagedlistdemo.variables.Constants.*;

public abstract class Repository {
    protected String urlBaseString;

    public Repository(String url, String... params){
        StringBuilder sb = new StringBuilder(BASE_URL + url + "?");
        for (String param : params) {
            sb.append(param);
            sb.append("&");
        }
        sb.setLength(sb.length()-1);
        urlBaseString = sb.toString();
    }

    protected String getResponseString(HttpURLConnection conn){
        StringBuilder sbJson = new StringBuilder();
        try(BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
            String line;
            while((line = in.readLine()) != null){
                sbJson.append(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return sbJson.toString();
    }
}
