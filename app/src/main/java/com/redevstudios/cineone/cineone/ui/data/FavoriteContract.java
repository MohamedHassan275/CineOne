package com.redevstudios.cineone.cineone.ui.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteContract {

    public static final String CONTENT_AUTHORITY = "com.redevstudios.cineone.cineone";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class FavoriteEntry implements BaseColumns {
        static String TABLE_NAME = "favorites";
        static String MOVIE_TITLE = "movie_title";
        static String MOVIE_OVERVIEW = "movie_overview";
        static String MOVIE_VOTE_COUNT = "movie_vote_count";
        static String MOVIE_VOTE_AVERAGE = "movie_vote_average";
        static String MOVIE_RELEASE_DATE = "movie_release_date";
        static String MOVIE_FAVORED = "movie_favored";
        static String MOVIE_POSTER_PATH = "movie_poster_path";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME)
                .build();

        public static Uri buildFavoriteUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }
    }
}
