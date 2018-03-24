package com.redevstudios.cineone.cineone.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.redevstudios.cineone.cineone.R;
import com.redevstudios.cineone.cineone.model.Movie;
import com.redevstudios.cineone.cineone.model.MoviePageResult;
import com.redevstudios.cineone.cineone.network.GetMovieDataService;
import com.redevstudios.cineone.cineone.network.RetrofitInstance;
import com.redevstudios.cineone.cineone.ui.adapter.MovieAdapter;
import com.redevstudios.cineone.cineone.ui.utils.EndlessRecyclerViewScrollListener;
import com.redevstudios.cineone.cineone.ui.utils.MovieClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "5806c9d1af02adb8387c8dc5b78eeab5";
    private GetMovieDataService movieDataService;
    private Call<MoviePageResult> call;
    private RecyclerView recyclerView;
    private EndlessRecyclerViewScrollListener scrollListener;
    List<Movie> movieResults;
    MovieAdapter movieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_movies);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        recyclerView.setLayoutManager(manager);

        initRecylerView();

        scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //TODO: Handle inputs of pages that exceed the total number of pages on the API
                //TODO: Fix weird flickering when scrolling back to the top of the RecyclerView
                loadNextPage(page + 1);
            }
        };

        recyclerView.addOnScrollListener(scrollListener);



    }

    private void initRecylerView() {
        movieDataService = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);
        call = movieDataService.getMovieData(1, API_KEY);

        call.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {
                movieResults = response.body().getMovieResult();

                movieAdapter = new MovieAdapter(movieResults, new MovieClickListener() {
                    @Override
                    public void onMovieClick(Movie movie) {
                        Log.wtf("click", "I was clicked");
                        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                        intent.putExtra("movie_title", movie.getTitle());
                        intent.putExtra("movie_vote_average", movie.getVoteAverage());
                        intent.putExtra("movie_vote_count", movie.getVoteCount());
                        intent.putExtra("movie_overview", movie.getOverview());
                        intent.putExtra("movie_release_date", movie.getReleaseDate());
                        intent.putExtra("movie_poster_path", movie.getPosterPath());
                        intent.putExtra("movie_backdrop_path", movie.getBackdropPath());
                        startActivity(intent);
                    }
                });

                recyclerView.setAdapter(movieAdapter);

            }

            @Override
            public void onFailure(Call<MoviePageResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNextPage(int pageNo) {
        movieDataService = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);
        call = movieDataService.getMovieData(pageNo, API_KEY);

        call.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {
                movieResults.addAll(response.body().getMovieResult());
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviePageResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}