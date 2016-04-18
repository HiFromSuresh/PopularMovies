package com.sureshssk2006.gmail.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private static final String ACTIVITYKEY = "activity_key";
    private static final String OBJECT_KEY = "object_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);

        if(savedInstanceState == null){
            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle args = new Bundle();
            Intent intent = getIntent();
            TMDBmovieList.TmdbMovee movie = (TMDBmovieList.TmdbMovee)intent.getParcelableExtra(ACTIVITYKEY);
            args.putParcelable(OBJECT_KEY, movie);
            detailsFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_container, detailsFragment)
                    .commit();
        }
    }
}
