package com.example.assignment2.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.assignment2.R;
import com.example.assignment2.databinding.ActivityMainBinding;
import com.example.assignment2.databinding.ActivityMovieDetailsScreenBinding;
import com.example.assignment2.viewmodel.MovieViewModel;


//(8) the last pages to implement are the activities

public class MovieDetailsScreenActivity extends AppCompatActivity {

    // Assignment 02 - COMP3025 - 02 - Friday 17:00 hrs
    // Susana Julia Cajavilca Turco - #200553998

    //2nd page

    // While debugging I realized that each binding name only works for one layout
    ActivityMovieDetailsScreenBinding binding;

    MovieViewModel movieViewModel;

    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMovieDetailsScreenBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());  // this replace -> setContentView(R.layout.activity_movie_details_screen);

        String imdbID = getIntent().getStringExtra("IMDB_ID");

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        movieViewModel.getMovieDetails().observe(this, movie -> {
            Log.d("MovieDetailsScreen", "Movie Details: " + movie.getTitle());
            if (movie != null) {
                // Update UI with movie details
                binding.titleMovie2.setText(movie.getTitle());
                binding.yearMovie2.setText(movie.getYear());
                binding.metascoreMovie.setText(movie.getMetascore());
                binding.directorMovie.setText(movie.getDirector());
                binding.plotMovie.setText(movie.getPlot());
                binding.genreMovie.setText(movie.getGenre());
                binding.runtimeMovie.setText(movie.getRuntime());

                // Load movie poster using Glide
                Glide.with(MovieDetailsScreenActivity.this)
                        .load(movie.getPoster() != null ? movie.getPoster() : R.drawable.no_poster_available)
                        .into(binding.imagePosterMovie2);
            }
            else {
                Log.e("MovieDetailsScreen", "Movie details not found");
            }
        });

        // Fetch the movie details using the IMDb ID
        if (imdbID != null) {
            movieViewModel.fetchMovieDetails(imdbID);
        }

        goBack = binding.returnButton;

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}