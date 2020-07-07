package com.example.pagedlistdemo.model;

import java.io.Serializable;
import java.util.Date;

public class Movie implements Serializable {
    private String movieName;
    private String plot;
    private String genre;
    private Date releaseDate;
    private String posterPath;

    public Movie(String movieName, String plot, String genre, Date date, String posterPath) {
        this.movieName = movieName;
        this.plot = plot;
        this.genre = genre;
        this.releaseDate = date;
        this.posterPath = posterPath;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getPlot() {
        return plot;
    }

    public String getGenre() {
        return genre;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}