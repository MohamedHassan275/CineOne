package com.redevstudios.cineone.cineone.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private static int totalPages;
    private static int currentSortMode = 1;
    private Call<MoviePageResult> call;
    private RecyclerView recyclerView;
    private List<Movie> movieResults;
    private MovieAdapter movieAdapter;



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

        loadPage(1);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((page + 1) <= totalPages) {
                    loadPage(page + 1);
                }
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //SortID 1 -> Popularity
        //SortID 2 -> Top rated
        switch (item.getItemId()) {
            case R.id.sort_by_popularity:
                currentSortMode = 1;
                break;
            case R.id.sort_by_top:
                currentSortMode = 2;
                break;
        }
        loadPage(1);
        return super.onOptionsItemSelected(item);

    }

    private void loadPage(final int page) {
        GetMovieDataService movieDataService = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);

        switch(currentSortMode){
            case 1:
                call = movieDataService.getPopularMovies(page, API_KEY);
                break;
            case 2:
                call = movieDataService.getTopRatedMovies(page, API_KEY);
                break;
        }


        call.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {

                if(page == 1) {
                    movieResults = response.body().getMovieResult();
                    totalPages = response.body().getTotalPages();

                    movieAdapter = new MovieAdapter(movieResults, new MovieClickListener() {
                        @Override
                        public void onMovieClick(Movie movie) {
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
                } else {
                    List<Movie> movies = response.body().getMovieResult();
                    for(Movie movie : movies){
                        movieResults.add(movie);
                        movieAdapter.notifyItemInserted(movieResults.size() - 1);
                    }
                }

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
    public static int getMeasuredPosterHeight(int width) {
        return (int) (width * 1.5f);
    }

    public static String movieImagePathBuilder(String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                imagePath;
    }

}