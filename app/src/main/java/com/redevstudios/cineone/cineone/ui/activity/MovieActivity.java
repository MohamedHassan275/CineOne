package com.redevstudios.cineone.cineone.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.redevstudios.cineone.cineone.R;
import com.squareup.picasso.Picasso;

import static com.redevstudios.cineone.cineone.ui.activity.MainActivity.movieImagePathBuilder;

@SuppressWarnings("ALL")
public class MovieActivity extends AppCompatActivity {

    private TextView mMovieTitle, mMovieOverview, mMovieReleaseDate, mMovieRating;
    private ImageView mMoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mMovieTitle = findViewById(R.id.movie_activity_title);
        mMoviePoster = findViewById(R.id.movie_activity_poster);
        mMovieOverview = findViewById(R.id.movie_activity_overview);
        mMovieReleaseDate = findViewById(R.id.movie_activity_release_date);
        mMovieRating = findViewById(R.id.movie_activity_rating);

        Intent intent = getIntent();
        String title = intent.getStringExtra("movie_title");
        String overview = intent.getStringExtra("movie_overview");
        double userRating = intent.getDoubleExtra("movie_vote_average", 0.0);
        int userRatingCount = intent.getIntExtra("movie_vote_count",0);
        String releaseDate = intent.getStringExtra("movie_release_date");
        String posterPath = intent.getStringExtra("movie_poster_path");
        String backdropPath = intent.getStringExtra("movie_backdrop_path");

        populateActivity(title, posterPath, overview, releaseDate, userRating);

    }

    private void populateActivity(String title, String posterPath, String overview, String releaseDate, double userRating){
        Picasso.with(this).load(movieImagePathBuilder(posterPath)).into(mMoviePoster);
        mMovieTitle.setText(title);
        mMovieOverview.setText(overview);
        mMovieReleaseDate.setText(releaseDate);

        String userRatingText = String.valueOf(userRating) + "/10";
        mMovieRating.setText(userRatingText);
    }


}
