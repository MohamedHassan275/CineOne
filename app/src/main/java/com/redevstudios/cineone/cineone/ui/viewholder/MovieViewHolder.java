package com.redevstudios.cineone.cineone.ui.viewholder;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.redevstudios.cineone.cineone.R;
import com.redevstudios.cineone.cineone.model.Movie;
import com.redevstudios.cineone.cineone.ui.utils.MovieClickListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Redouane on 3/10/2018.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {
    private ImageView moviePoster;

    public MovieViewHolder(final View itemView) {
        super(itemView);
        moviePoster = itemView.findViewById(R.id.iv_movie_poster);

    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public void bind(final Movie movie, final MovieClickListener movieClickListener) {
        Picasso.with(moviePoster.getContext()).load(moviePosterPath(movie.getPosterPath())).resize(getScreenWidth() / 2, 0).into(moviePoster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieClickListener.onMovieClick(movie);
            }
        });
    }

    private String moviePosterPath(String moviePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                moviePath;
    }

}
