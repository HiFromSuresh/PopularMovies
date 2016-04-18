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
    OnItemClickListener mItemClickListener;

    public FavoritesCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        DatabaseUtils.dumpCursor(cursor);
        int posterIndex = cursor.getColumnIndex(FavoriteMovieColumns.POSTER);
        int movieIdIndex = cursor.getColumnIndex(FavoriteMovieColumns.MOVIE_ID);
        viewHolder.favoritesImageview.setImageURI(Uri.parse(cursor.getString(posterIndex)));
        viewHolder.favoritesImageview.setTag(cursor.getString(movieIdIndex));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorites_list_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        mVh = vh;
        return vh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView favoritesImageview;

        public ViewHolder(View itemView) {
            super(itemView);
            favoritesImageview = (ImageView) itemView.findViewById(R.id.favorites_imageview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
