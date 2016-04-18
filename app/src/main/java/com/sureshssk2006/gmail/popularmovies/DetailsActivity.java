package com.sureshssk2006.gmail.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);

        if(savedInstanceState == null){
            DetailsFragment detailsFragment = new DetailsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_container, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
