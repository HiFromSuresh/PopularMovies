package com.sureshssk2006.gmail.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        viewHolder.titleTextView.setText(
                cursor.getString(cursor.getColumnIndex(FavoriteMovieColumns.ORIGINAL_TITLE))
        );
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
        public TextView titleTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.favorites_list_txtView);
        }
    }
}
