package com.sureshssk2006.gmail.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MovieListFragment.Callbackk,
        MovieListFragment.FavoritesCallback,
        FavoritesFragment.FavoritesItemCallback {

    private static final String DETAIL_FRAG = "DFTAG";
    private static final String OBJECT_KEY = "object_key";
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private static final String ACTIVITYKEY = "activity_key";
    private static final String FAVORITEKEY = "favorite_key";
    private static final String FAVORITEACTIVITYKEY = "favorite_activity_key";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new MovieListFragment())
                    .commit();
        }
        if (findViewById(R.id.details_container) != null) {
            mTwoPane = true;
            /*if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.details_container, new DetailsFragment(), DETAIL_FRAG)
                        .commit();
            }*/
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onItemSelected(TMDBmovieList.TmdbMovee movie) {
        if (mTwoPane) {

            Bundle args = new Bundle();
            args.putParcelable(OBJECT_KEY, movie);

            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_container, detailsFragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(ACTIVITYKEY, movie);
            startActivity(intent);
        }
    }

    @Override
    public void onFavoriteSelected() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new FavoritesFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFavoriteItemClicked(String id) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putString(FAVORITEKEY, id);

            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_container, detailsFragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(FAVORITEACTIVITYKEY, id);
            startActivity(intent);
        }
    }
}
