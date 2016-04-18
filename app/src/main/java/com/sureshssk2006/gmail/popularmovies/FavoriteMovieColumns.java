package com.sureshssk2006.gmail.popularmovies;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Administrator on 4/12/2016.
 */
public interface FavoriteMovieColumns {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";
    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String MOVIE_ID = "movie_id";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String ORIGINAL_TITLE = "original_title";
    @DataType(DataType.Type.TEXT) //add not null later
    public static final String POSTER = "poster";
    @DataType(DataType.Type.TEXT)
    public static final String OVERVIEW = "overview";
    @DataType(DataType.Type.TEXT)
    public static final String RELEASEYEAR = "release_year";
    @DataType(DataType.Type.TEXT)
    public static final String RATING = "rating";
}
