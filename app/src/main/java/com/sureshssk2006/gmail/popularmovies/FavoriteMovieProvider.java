package com.sureshssk2006.gmail.popularmovies;

import android.net.Uri;
import android.util.Log;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Administrator on 4/12/2016.
 */
@ContentProvider(authority = FavoriteMovieProvider.AUTHORITY, database = FavoriteMovieDatabase.class)
public class FavoriteMovieProvider {

    public static final String AUTHORITY = "com.sureshssk2006.gmail.popularmovies";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String FAVORITE_MOVIES = "favorite_movies";
    }

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        Log.d("TAG", builder.toString());
        return builder.build();
    }

    @TableEndpoint(table = FavoriteMovieDatabase.FAVORITE_MOVIES)
    public static class FavoriteMovies{
        @ContentUri(
                path = Path.FAVORITE_MOVIES,
                type = "vnd.android.cursor.dir/favorite_movie",
                defaultSort = FavoriteMovieColumns._ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.FAVORITE_MOVIES);

        @InexactContentUri(
                name = "MOVIE_ID",
                path = Path.FAVORITE_MOVIES + "/#",
                type = "vnd.android.cursor.item/movie",
                whereColumn = FavoriteMovieColumns.MOVIE_ID,
                pathSegment = 1)
        public static Uri withId(long id){
            return buildUri(Path.FAVORITE_MOVIES, String.valueOf(id));
        }
    }
}
