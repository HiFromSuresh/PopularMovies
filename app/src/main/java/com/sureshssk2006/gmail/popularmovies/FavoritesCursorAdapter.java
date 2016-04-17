package com.sureshssk2006.gmail.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 4/15/2016.
 */
public class FavoritesCursorAdapter extends CursorRecyclerViewAdapter<FavoritesCursorAdapter.ViewHolder> {
    Context mContext;
    ViewHolder mVh;

    public FavoritesCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext =context;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        DatabaseUtils.dumpCursor(cursor);
        int index = cursor.getColumnIndex(FavoriteMovieColumns.POSTER);
        viewHolder.favoritesImageview.setImageURI(Uri.parse(cursor.getString(index)));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorites_list_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        mVh = vh;
        return vh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView favoritesImageview;
        public ViewHolder(View itemView) {
            super(itemView);
            favoritesImageview = (ImageView) itemView.findViewById(R.id.favorites_imageview);
        }
    }
}
