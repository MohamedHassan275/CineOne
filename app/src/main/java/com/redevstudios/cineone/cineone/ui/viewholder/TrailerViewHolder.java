package com.redevstudios.cineone.cineone.ui.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.redevstudios.cineone.cineone.R;
import com.redevstudios.cineone.cineone.model.Movie;
import com.redevstudios.cineone.cineone.model.MovieTrailer;
import com.redevstudios.cineone.cineone.ui.utils.MovieClickListener;
import com.redevstudios.cineone.cineone.ui.utils.TrailerClickListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.redevstudios.cineone.cineone.ui.activity.MainActivity.getMeasuredPosterHeight;
import static com.redevstudios.cineone.cineone.ui.activity.MainActivity.getScreenWidth;
import static com.redevstudios.cineone.cineone.ui.activity.MainActivity.movieImagePathBuilder;

public class TrailerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_movie_trailer_name)
    TextView mMovieTrailerName;
    @BindView(R.id.cv_movie_trailer_card)
    CardView mMovieCard;

    public TrailerViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final MovieTrailer mMovieTrailer, final TrailerClickListener mTrailerClickListener) {
        mMovieTrailerName.setText(mMovieTrailer.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrailerClickListener.onMovieTrailerClick(mMovieTrailer);
            }
        });
    }
}
