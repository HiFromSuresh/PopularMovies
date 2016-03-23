package com.sureshssk2006.gmail.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 3/22/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final Context context;
    private List<TMDBmovieList.TmdbMovee> movieArrayList;
    final String BASE_URL = "http://image.tmdb.org/t/p/";
    final String POSTER_SIZE = "w185";

    public MovieAdapter(Context context,List<TMDBmovieList.TmdbMovee> items) {
        this.movieArrayList = items;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(posterPath(position)).into(holder.posterView);
    }

    private String posterPath(int position) {
        TMDBmovieList.TmdbMovee movie = movieArrayList.get(position);
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(POSTER_SIZE)
                .appendEncodedPath(movie.getPoster_path())
                .build();
        return builtUri.toString();
    }

    @Override
    public int getItemCount() {
        if (movieArrayList == null){
            return -1;
        }
        return movieArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView posterView;
        public ViewHolder(View itemView) {
            super(itemView);
            posterView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }

    public void swapList(List<TMDBmovieList.TmdbMovee> items){
        this.movieArrayList = items;
        notifyDataSetChanged();
    }
}
