package com.example.pagedlistdemo.client;

import android.os.AsyncTask;

import com.example.pagedlistdemo.dao.GenreListRepo;

import java.util.Map;

public class GenreListClient extends AsyncTask<Map<Integer, String>, Void, Integer> {
    GenreListRepo genreListRepo;

    protected GenreListClient(){
        genreListRepo = new GenreListRepo();
    }

    @SafeVarargs
    @Override
    protected final Integer doInBackground(Map<Integer, String>... maps) {
        return genreListRepo.loadToMap(maps[0]);
    }


}
