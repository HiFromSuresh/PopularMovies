package com.sureshssk2006.gmail.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private static final String ACTIVITYKEY = "activity_key";
    private static final String OBJECT_KEY = "object_key";
    private static final String FAVORITEACTIVITYKEY = "favorite_activity_key";
    private final String FAVORITEKEY = "favorite_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);

        Intent intent = getIntent();
        TMDBmovieList.TmdbMovee movie = (TMDBmovieList.TmdbMovee) intent.getParcelableExtra(ACTIVITYKEY);
        String id = intent.getStringExtra(FAVORITEACTIVITYKEY);

        if (savedInstanceState == null && movie != null) {
            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle args = new Bundle();
            args.putParcelable(OBJECT_KEY, movie);
            detailsFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_container, detailsFragment)
                    .commit();
        }

        if (savedInstanceState == null && id != null) {
            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle args = new Bundle();
            args.putString(FAVORITEKEY, id);
            detailsFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_container, detailsFragment)
                    .commit();
        }
    }
}
