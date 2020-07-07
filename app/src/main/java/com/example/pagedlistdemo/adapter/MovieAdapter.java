package com.example.pagedlistdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pagedlistdemo.R;
import com.example.pagedlistdemo.activity.MainActivity;
import com.example.pagedlistdemo.activity.MovieInformation;
import com.example.pagedlistdemo.databinding.RecylerviewItemBinding;
import com.example.pagedlistdemo.model.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private List<Movie> items;
    private Context ctx;

    public MovieAdapter(List<Movie> items, Context ctx){
        this.items = items;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(RecylerviewItemBinding.inflate(LayoutInflater.from(ctx)));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        Glide.with(ctx)
                .load("https://image.tmdb.org/t/p/w500"+items.get(position).getPosterPath())
                .into(holder.imageView);
        String textMovieName = items.get(position).getMovieName();
        holder.textView.setText(textMovieName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, MovieInformation.class);
                intent.putExtra("movie", items.get(position));
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        RecylerviewItemBinding binding;

        public MovieViewHolder(RecylerviewItemBinding b){
            super(b.getRoot());
            textView = b.textViewName;
            imageView = b.imageView;
            binding = b;
        }


    }

}
