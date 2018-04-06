package com.redevstudios.cineone.cineone.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.redevstudios.cineone.cineone.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.redevstudios.cineone.cineone.ui.activity.MainActivity.movieImagePathBuilder;

@SuppressWarnings("ALL")
public class MovieActivity extends AppCompatActivity {
    @BindView(R.id.movie_activity_title) TextView mMovieTitle;
    @BindView(R.id.movie_activity_poster) ImageView mMoviePoster;
    @BindView(R.id.movie_activity_overview) TextView mMovieOverview;
    @BindView(R.id.movie_activity_release_date) TextView mMovieReleaseDate;
    @BindView(R.id.movie_activity_rating) TextView mMovieRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ButterKnife.bind(this);

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
