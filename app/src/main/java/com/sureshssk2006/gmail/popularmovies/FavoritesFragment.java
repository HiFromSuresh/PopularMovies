package com.sureshssk2006.gmail.popularmovies;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    private static final int CURSOR_LOADER_ID = 0;
    private FavoritesCursorAdapter mFavoritesCursorAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
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
        recyclerView.setLayoutManager(
                new LinearLayoutManager(recyclerView.getContext())
        );

        mFavoritesCursorAdapter = new FavoritesCursorAdapter(getActivity(), null);
        recyclerView.setAdapter(mFavoritesCursorAdapter);
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
