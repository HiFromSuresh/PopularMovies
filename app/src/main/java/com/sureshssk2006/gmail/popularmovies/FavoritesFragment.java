package com.sureshssk2006.gmail.popularmovies;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    private static final int CURSOR_LOADER_ID = 0;
    private static final String KEY = "key";
    private FavoritesCursorAdapter mFavoritesCursorAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public interface FavoritesItemCallback{
        public void onFavoriteItemClicked(String id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Cursor cursor = getActivity().getContentResolver().query(FavoriteMovieProvider.FavoriteMovies.CONTENT_URI,
                null, null, null, null);
        if (cursor == null || cursor.getCount() == 0){
            Toast.makeText(getContext(), "No favorite movies added yet", Toast.LENGTH_LONG).show();
        }

        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.favorites_recyclerview);
        if(getResources().getConfiguration().orientation == 1) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        recyclerView.setHasFixedSize(true);

        mFavoritesCursorAdapter = new FavoritesCursorAdapter(getActivity(), null);
        recyclerView.setAdapter(mFavoritesCursorAdapter);

        mFavoritesCursorAdapter.SetOnItemClickListener(new FavoritesCursorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String movieId = (String) view.getTag();
                ((FavoritesItemCallback)getActivity()).onFavoriteItemClicked(movieId);
                /*DetailsFragment detailsFragment = new DetailsFragment();
                Bundle args = new Bundle();
                args.putString(KEY, movieId);
                detailsFragment.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.details_container, detailsFragment)
                        .addToBackStack(null)
                        .commit();*/
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), FavoriteMovieProvider.FavoriteMovies.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFavoritesCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavoritesCursorAdapter.swapCursor(null);
    }
}
