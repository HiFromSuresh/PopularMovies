package com.sureshssk2006.gmail.popularmovies;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Administrator on 4/12/2016.
 */
@Database(version = FavoriteMovieDatabase.VERSION)
public final class FavoriteMovieDatabase {

    private FavoriteMovieDatabase() {
    }

    public static final int VERSION = 1;

    @Table(FavoriteMovieColumns.class)
    public static final String FAVORITE_MOVIES = "favorite_movies";

}
