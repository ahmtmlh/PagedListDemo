package com.example.pagedlistdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pagedlistdemo.R;
import com.example.pagedlistdemo.adapter.MovieAdapter;
import com.example.pagedlistdemo.client.MovieClient;
import com.example.pagedlistdemo.variables.Lists;
import com.example.pagedlistdemo.databinding.ActivityMainBinding;
import com.example.pagedlistdemo.model.GenreList;
import com.example.pagedlistdemo.model.Movie;
import com.example.pagedlistdemo.client.GenreListClientFactory;
import com.example.pagedlistdemo.client.MovieClientFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button loadMoreButton;
    private int PAGE_COUNT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(isOnline()){
            initWidgets();
        } else {
            Toast.makeText(this, "NO CONNECTION. PLEASE CONNECT AND RESTART", Toast.LENGTH_LONG).show();
        }
    }

    private void initWidgets(){
        GenreListClientFactory genreListClientFactory = new GenreListClientFactory();
        try {
            //noinspection unchecked
            int ret = genreListClientFactory.getGenreListClient().execute(GenreList.genreMappings).get();
            if (ret != 0){
                throw new Exception("Genre List obtain error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Genre List Can't be obtained", Toast.LENGTH_LONG).show();
        }
        recyclerView = findViewById(R.id.recyclerView);
        loadMoreButton = findViewById(R.id.loadButton);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    loadMoreButton.setVisibility(View.VISIBLE);
                } else {
                    loadMoreButton.setVisibility(View.GONE);
                }
            }
        });
        final MovieClientFactory movieServiceFactory = new MovieClientFactory();
        loadItems(movieServiceFactory);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextPage(movieServiceFactory);
                loadMoreButton.setVisibility(View.GONE);
            }
        });
    }

    private void loadItems(MovieClientFactory movieClientFactory){

        List<Movie> movies = null;
        try {
            movies = movieClientFactory.createMovieClient().execute(PAGE_COUNT).get();
            Lists.adapterItems.addAll(movies);
            recyclerView.setAdapter(new MovieAdapter(Lists.adapterItems, this));
            PAGE_COUNT++;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadNextPage(MovieClientFactory movieClientFactory){
        List<Movie> movies = null;
        try {
            movies = movieClientFactory.createMovieClient().execute(PAGE_COUNT).get();
            Lists.adapterItems.addAll(movies);
            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            PAGE_COUNT++;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

}