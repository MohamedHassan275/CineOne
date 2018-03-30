package com.redevstudios.cineone.cineone.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.redevstudios.cineone.cineone.R;
import com.squareup.picasso.Picasso;

import static com.redevstudios.cineone.cineone.ui.activity.MainActivity.getScreenWidth;
import static com.redevstudios.cineone.cineone.ui.activity.MainActivity.movieImagePathBuilder;

public class MovieActivity extends AppCompatActivity {

    private TextView movieTitle, movieOverview;
    private ImageView moviePoster, movieBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movieTitle = findViewById(R.id.movie_activity_title);
        moviePoster = findViewById(R.id.movie_activity_poster);
        movieOverview = findViewById(R.id.movie_activity_overview);

        Intent intent = getIntent();
        String title = intent.getStringExtra("movie_title");
        String overview = intent.getStringExtra("movie_overview");
        double userRating = intent.getDoubleExtra("movie_vote_average", 0.0);
        int userRatingCount = intent.getIntExtra("movie_vote_count",0);
        String releaseDate = intent.getStringExtra("movie_release_date");
        String posterPath = intent.getStringExtra("movie_poster_path");
        String backdropPath = intent.getStringExtra("movie_backdrop_path");

        populateActivity(title, posterPath, overview);

    }

    private void populateActivity(String title, String posterPath, String overview){
        Picasso.with(this).load(movieImagePathBuilder(posterPath)).into(moviePoster);
        movieTitle.setText(title);
        movieOverview.setText(overview);
    }


}
