package com.example.pagedlistdemo.dao;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.pagedlistdemo.model.GenreList;
import com.example.pagedlistdemo.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.pagedlistdemo.variables.Constants.*;

public class MovieRepo extends Repository{

    public MovieRepo(){
        super("movie/top_rated", "api_key=" + API_KEY);
    }

    public List<Movie> getTopRatedMoviesByPage(int page) {
        List<Movie> movieList = null;
        try{
            URL url = new URL(getUrlWithPage(page));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(SERVER_READ_TIMEOUT);
            conn.setConnectTimeout(SERVER_CONNECTION_TIMEOUT);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            // Send request
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                String jsonData = getResponseString(conn);
                movieList = saveJsonData(jsonData);
            } else {
                Log.e("ERROR", "CAN'T GET MOVIES");
            }
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    private String getUrlWithPage(int page){
        return urlBaseString.concat("&page=").concat(String.valueOf(page));
    }

    private List<Movie> saveJsonData(String jsonData){
        List<Movie> resultList = new ArrayList<>();
        try{
            JSONObject root = new JSONObject(jsonData);
            JSONArray results = root.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject res = results.getJSONObject(i);
                String movieName = res.getString("title");
                String plot = res.getString("overview");
                Date date;
                try{
                    date = stringToDate(res.getString("release_date"));
                } catch (ParseException e){
                    date = new Date(0);
                }
                String genre = GenreList.getGenreFromId(res.getJSONArray("genre_ids").getInt(0));
                String posterPath = res.getString("poster_path");
                Movie temp = new Movie(movieName, plot, genre, date, posterPath);
                resultList.add(temp);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return resultList;
    }

    private Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return formatter.parse(dateString);
    }

}