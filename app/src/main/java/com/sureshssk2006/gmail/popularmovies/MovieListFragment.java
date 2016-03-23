package com.sureshssk2006.gmail.popularmovies;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment {


    private SharedPreferences sharedPreferences;
    private String sortByValue;
    private MovieAdapter movieAdapter;
    private List<TMDBmovieList.TmdbMovee> items;
    private Retrofit retrofit;
    final String BASE_URL = "https://api.themoviedb.org/";
    private String apiKeyVAlue;
    private Call<TMDBmovieList> call;
    private TMDBmovieList movieList;

    public MovieListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        sharedPreferences = getActivity().getSharedPreferences("PrefData", Context.MODE_PRIVATE);
        sortByValue = sharedPreferences
                .getString("SORT_VALUE", getResources().getString(R.string.popularity_desc));
        apiKeyVAlue = BuildConfig.TMDB_API_KEY;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        movieAdapter = new MovieAdapter(getContext(), items);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.movie_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(movieAdapter);
        TMDBService.TMDBapi tmdBapi = retrofit.create(TMDBService.TMDBapi.class);
        call = tmdBapi.getMovies(sortByValue, apiKeyVAlue);
        call.enqueue(new Callback<TMDBmovieList>() {
            @Override
            public void onResponse(Response<TMDBmovieList> response) {
                try {
                    Log.d("TAG", "onResponse: " + response.body().toString());
                    movieList = response.body();
                    items = movieList.getResults();
                    movieAdapter.swapList(items);
                } catch (NullPointerException e) {
                    Toast.makeText(getContext(), "Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Onfailure called", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }


}
