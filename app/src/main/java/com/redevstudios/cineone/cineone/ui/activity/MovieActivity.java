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
import com.redevstudios.cineone.cineone.model.MovieReviewPageResult;
import com.redevstudios.cineone.cineone.model.MovieTrailerPageResult;
import com.redevstudios.cineone.cineone.network.GetMovieReviewService;
import com.redevstudios.cineone.cineone.network.GetMovieTrailerService;
import com.redevstudios.cineone.cineone.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

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
        Bundle bundle = intent.getExtras();
        Movie mMovie = (Movie) bundle.getSerializable("movie");

        getTrailer(mMovie.getId());
        getReviews(mMovie.getId());
        
        populateActivity(mMovie);

    }

    private void populateActivity(Movie mMovie){
        Picasso.with(this).load(movieImagePathBuilder(mMovie.getPosterPath())).into(mMoviePoster);
        mMovieTitle.setText(mMovie.getTitle());
        mMovieOverview.setText(mMovie.getOverview());
        mMovieReleaseDate.setText(mMovie.getReleaseDate());

        String userRatingText = String.valueOf(mMovie.getVoteAverage()) + "/10";
        mMovieRating.setText(userRatingText);
    }

    private void getTrailer(int movieId) {
        GetMovieTrailerService movieTrailerService = RetrofitInstance.getRetrofitInstance().create(GetMovieTrailerService.class);
        Call<MovieTrailerPageResult> call = movieTrailerService.getTrailers(movieId, API_KEY);


        call.enqueue(new Callback<MovieTrailerPageResult>() {
            @Override
            public void onResponse(Call<MovieTrailerPageResult> call, Response<MovieTrailerPageResult> response) {
                Log.wtf("MovieActivity", "http://youtube.com/watch?v=" + response.body().getTrailerResult().get(0).getKey());

            }

            @Override
            public void onFailure(Call<MovieTrailerPageResult> call, Throwable t) {
                Toast.makeText(MovieActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getReviews(int movieId) {
        GetMovieReviewService mGetMovieReviewService = RetrofitInstance.getRetrofitInstance().create(GetMovieReviewService.class);
        Call<MovieReviewPageResult> call = mGetMovieReviewService.getReviews(movieId, API_KEY);


        call.enqueue(new Callback<MovieReviewPageResult>() {
            @Override
            public void onResponse(Call<MovieReviewPageResult> call, Response<MovieReviewPageResult> response) {
                Log.wtf("MovieActivity", response.body().getResults().get(0).getContent());
            }

            @Override
            public void onFailure(Call<MovieReviewPageResult> call, Throwable t) {
                Toast.makeText(MovieActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
