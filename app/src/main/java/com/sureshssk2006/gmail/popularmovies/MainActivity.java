package com.sureshssk2006.gmail.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MovieListFragment.Callbackk {

    private static final String DETAIL_FRAG = "DFTAG";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.details_container) != null){
            mTwoPane = true;
            if(savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.details_container, new DetailsFragment(), DETAIL_FRAG)
                        .commit();
            }else {
                mTwoPane = false;
            }
        }
    }

    @Override
    public void onItemSelected(TMDBmovieList.TmdbMovee movie) {
        Toast.makeText(this, movie.getOriginal_title(), Toast.LENGTH_SHORT).show();
    }
}
