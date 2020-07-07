package com.example.pagedlistdemo.model;

import com.example.pagedlistdemo.dao.GenreListRepo;

import java.util.HashMap;
import java.util.Map;

public class GenreList {

    public static Map<Integer, String> genreMappings = new HashMap<>();

    public static String getGenreFromId(int id){
        if(GenreList.genreMappings.containsKey(id)){
            return GenreList.genreMappings.get(id);
        } else {
            return "";
        }

    }
}
