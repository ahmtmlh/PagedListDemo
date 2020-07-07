package com.example.pagedlistdemo.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static com.example.pagedlistdemo.variables.Constants.*;


public class GenreListRepo extends Repository{

    public GenreListRepo(){
        super("genre/movie/list",  "api_key=" + API_KEY, "language=en-US");
    }

    public int loadToMap(Map<Integer, String> mappings){
        int ret = 0;
        try{
            URL url = new URL(urlBaseString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                String jsonData = getResponseString(conn);
                parseAndLoad(mappings, jsonData);
            } else {
                Log.e("ERROR", "CAN'T GET GENRES. Server Response: " + responseCode);
                ret = -1;
            }
            conn.disconnect();
        } catch (IOException e){
            e.printStackTrace();
        }
        return ret;
    }
    private void parseAndLoad(Map<Integer, String> map, String jsonStr){
        try{
            JSONObject root = new JSONObject(jsonStr);
            JSONArray genres = root.getJSONArray("genres");
            for (int i = 0; i < genres.length(); i++) {
                JSONObject genre = genres.getJSONObject(i);
                map.put(genre.getInt("id"), genre.getString("name"));
            }
        } catch (JSONException e){
            Log.e("ERROR", "JSON PARSE ERROR");
            e.printStackTrace();
        }

    }
}
