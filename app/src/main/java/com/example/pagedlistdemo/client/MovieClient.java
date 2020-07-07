package com.example.pagedlistdemo.client;

import android.os.AsyncTask;

import com.example.pagedlistdemo.dao.MovieRepo;
import com.example.pagedlistdemo.model.Movie;

import java.util.List;

public class MovieClient extends AsyncTask<Integer, Void, List<Movie>> {
    private MovieRepo movieRepo;
    protected MovieClient(){
        movieRepo = new MovieRepo();
    }

    @Override
    protected List<Movie> doInBackground(Integer... pages) {
        return movieRepo.getTopRatedMoviesByPage(pages[0]);
    }

}
