package com.redevstudios.cineone.cineone.ui.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.redevstudios.cineone.cineone.R;
import com.redevstudios.cineone.cineone.model.Movie;
import com.redevstudios.cineone.cineone.ui.utils.MovieClickListener;
import com.squareup.picasso.Picasso;

import static com.redevstudios.cineone.cineone.ui.activity.MainActivity.getMeasuredPosterHeight;
import static com.redevstudios.cineone.cineone.ui.activity.MainActivity.getScreenWidth;
import static com.redevstudios.cineone.cineone.ui.activity.MainActivity.movieImagePathBuilder;

/**
 * Created by Redouane on 3/10/2018.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {
    private ImageView moviePoster;
    private CardView movieCard;

    public MovieViewHolder(final View itemView) {
        super(itemView);
        moviePoster = itemView.findViewById(R.id.iv_movie_poster);
        movieCard = itemView.findViewById(R.id.cv_movie_card);
    }

    public void bind(final Movie movie, final MovieClickListener movieClickListener) {

        movieCard.setLayoutParams(new ViewGroup.LayoutParams(getScreenWidth()/2, getMeasuredPosterHeight(getScreenWidth()/2)));

        Picasso.with(moviePoster.getContext()).load(movieImagePathBuilder(movie.getPosterPath())).placeholder(R.drawable.placeholder).fit().centerCrop().into(moviePoster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieClickListener.onMovieClick(movie);
            }
        });
    }
}
