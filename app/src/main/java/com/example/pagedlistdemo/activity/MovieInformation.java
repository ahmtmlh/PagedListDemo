package com.example.pagedlistdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pagedlistdemo.R;
import com.example.pagedlistdemo.model.Movie;

import java.util.Objects;

public class MovieInformation extends AppCompatActivity {

    private ImageView image;
    private TextView title;
    private TextView plot;
    private TextView genre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);
        image = findViewById(R.id.iv_movie_poster);
        title = findViewById(R.id.movie_title);
        plot = findViewById(R.id.movie_overview);
        genre = findViewById(R.id.movie_genre);

        Intent intent = getIntent();
        Movie movie = (Movie) Objects.requireNonNull(intent.getExtras()).getSerializable("movie");
        // Better be safe than sorry
        assert movie != null;
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+movie.getPosterPath())
                .into(image);
        title.setText(movie.getMovieName());
        plot.setText(movie.getPlot());
        genre.setText(movie.getGenre());
    }
}