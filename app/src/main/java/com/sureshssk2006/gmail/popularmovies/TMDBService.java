package com.sureshssk2006.gmail.popularmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 3/23/2016.
 */
public class TMDBService {
    public interface TMDBapi {

        @GET("3/discover/movie?")
        Call<TMDBmovieList> getMovies(
                @Query("sort_by") String sortByValue,
                @Query("api_key") String apiKeyVAlue);
    }
}
