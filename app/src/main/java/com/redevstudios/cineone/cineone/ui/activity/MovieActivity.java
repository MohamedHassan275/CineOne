package com.redevstudios.cineone.cineone.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.redevstudios.cineone.cineone.R;
import com.redevstudios.cineone.cineone.model.Movie;
import com.redevstudios.cineone.cineone.model.MoviePageResult;
import com.redevstudios.cineone.cineone.model.MovieTrailer;
import com.redevstudios.cineone.cineone.model.MovieTrailerResult;
import com.redevstudios.cineone.cineone.network.GetMovieDataService;
import com.redevstudios.cineone.cineone.network.GetMovieTrailerService;
import com.redevstudios.cineone.cineone.network.RetrofitInstance;
import com.redevstudios.cineone.cineone.ui.adapter.MovieAdapter;
import com.redevstudios.cineone.cineone.ui.utils.MovieClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.redevstudios.cineone.cineone.ui.activity.MainActivity.API_KEY;
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
        int id = intent.getIntExtra("movie_id", 0);
        String title = intent.getStringExtra("movie_title");
        String overview = intent.getStringExtra("movie_overview");
        double userRating = intent.getDoubleExtra("movie_vote_average", 0.0);
        int userRatingCount = intent.getIntExtra("movie_vote_count",0);
        String releaseDate = intent.getStringExtra("movie_release_date");
        String posterPath = intent.getStringExtra("movie_poster_path");
        String backdropPath = intent.getStringExtra("movie_backdrop_path");

        getTrailer(id);
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

    private void getTrailer(int movieId) {
        GetMovieTrailerService movieTrailerService = RetrofitInstance.getRetrofitInstance().create(GetMovieTrailerService.class);
        Call<MovieTrailerResult> call = movieTrailerService.getTrailers(movieId, API_KEY);


        call.enqueue(new Callback<MovieTrailerResult>() {
            @Override
            public void onResponse(Call<MovieTrailerResult> call, Response<MovieTrailerResult> response) {
                Log.wtf("MovieActivity", "http://youtube.com/watch?v=" + response.body().getTrailerResult().get(0).getKey());

            }

            @Override
            public void onFailure(Call<MovieTrailerResult> call, Throwable t) {
                Toast.makeText(MovieActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
