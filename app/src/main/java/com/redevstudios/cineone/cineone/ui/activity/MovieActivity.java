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

public class MovieActivity extends AppCompatActivity {

    private TextView movieTitle;
    private ImageView moviePoster, movieBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movieTitle = findViewById(R.id.movie_activity_title);
        moviePoster = findViewById(R.id.movie_activity_poster);
        movieBackdrop = findViewById(R.id.movie_activity_backdrop);

        Intent intent = getIntent();
        String title = intent.getStringExtra("movie_title");
        String overview = intent.getStringExtra("movie_overview");
        double userRating = intent.getDoubleExtra("movie_vote_average", 0.0);
        int userRatingCount = intent.getIntExtra("movie_vote_count",0);
        String releaseDate = intent.getStringExtra("movie_release_date");
        String posterPath = intent.getStringExtra("movie_poster_path");
        String backdropPath = intent.getStringExtra("movie_backdrop_path");

        populateActivity(title, posterPath, backdropPath);


        Log.wtf("TITLE", title);
        Log.wtf("OVERVIEW", overview);
        Log.wtf("USER RATING", String.valueOf(userRating));
        Log.wtf("USER RATING COUNT", String.valueOf(userRatingCount));
        Log.wtf("RELEASE DATE", releaseDate);
        Log.wtf("POSTER PATH", posterPath);



    }

    private void populateActivity(String title, String posterPath, String backdropPath){
        Picasso.with(this).load(movieImagePathBuilder(posterPath)).into(moviePoster);
        movieTitle.setText(title);
        Picasso.with(this).load(movieImagePathBuilder(backdropPath)).resize(getScreenWidth(), 0).into(movieBackdrop);
    }

    private String movieImagePathBuilder(String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                imagePath;
    }

}
