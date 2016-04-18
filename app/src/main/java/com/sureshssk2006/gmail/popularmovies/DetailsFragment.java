package com.sureshssk2006.gmail.popularmovies;


import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = DetailsFragment.class.getSimpleName();

    private static final String YOUTUBE_KEY = "v";
    private static final int CURSOR_LOADER_ID = 1;
    final String BASE_URL = "https://api.themoviedb.org/";
    final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?";
    Call<TmdbTrailersList> call;
    private String apiKeyVAlue;
    LinearLayout trailerLayout;
    LinearLayout reviewLayout;
    TextView reviewBtn;
    Button markFavoriteBtn;
    boolean isFavorite = false;
    boolean loadFromDB = false;
    long movieIdLong;
    String movieId;

    private final String OBJECT_KEY = "object_key";
    private final String FAVORITEKEY = "favorite_key";
    private TextView mTitleTextView;
    private ImageView mImageView;
    private TextView mOverviewTextView;
    private TextView mReleasedateTextView;
    private TextView mRatingTextView;
    TMDBmovieList.TmdbMovee tmdbMovie;
    String movieIdFromFavoriteFragment;
    private TmdbTrailersList trailerList;
    private TmdbReviewList reviewList;
    private List<TmdbTrailersList.TmdbTrailer> trailers;
    private List<TmdbReviewList.TmdbReview> reviews;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        //Typecasting
        mTitleTextView = (TextView) rootView.findViewById(R.id.details_original_title);
        mImageView = (ImageView) rootView.findViewById(R.id.details_image_poster);
        mOverviewTextView = (TextView) rootView.findViewById(R.id.details_plot_synopsis);
        mRatingTextView = (TextView) rootView.findViewById(R.id.details_rating);
        mReleasedateTextView = (TextView) rootView.findViewById(R.id.details_release_date);
        trailerLayout = (LinearLayout) rootView.findViewById(R.id.trailers_layout);
        reviewLayout = (LinearLayout) rootView.findViewById(R.id.review_layout);
        reviewBtn = (TextView) rootView.findViewById(R.id.review_button);
        markFavoriteBtn = (Button) rootView.findViewById(R.id.btn_favorite);


        //get Data from bundle
        Bundle bundle = this.getArguments();
        tmdbMovie = bundle.getParcelable(OBJECT_KEY);
        movieIdFromFavoriteFragment = bundle.getString(FAVORITEKEY);

        if(movieIdFromFavoriteFragment != null)loadFromDB = true;

        if(!loadFromDB) {
            mTitleTextView.setText(tmdbMovie.getOriginal_title());
            Picasso.with(getContext()).load(tmdbMovie.getFullPosterpath()).into(mImageView);
            mOverviewTextView.setText(tmdbMovie.getOverview());
            mRatingTextView.setText(tmdbMovie.getVote_average());
            mReleasedateTextView.setText(dateToYear(tmdbMovie.getRelease_date()));
            movieId = tmdbMovie.getId();
        }

        if(loadFromDB){
            movieId = movieIdFromFavoriteFragment;
            isFavorite = true;
        }



        apiKeyVAlue = BuildConfig.TMDB_API_KEY;
        addTrailers();

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReviews();
                reviewBtn.setClickable(false);
            }
        });



        return rootView;
    }

    private void addTrailers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDBService.TMDBapi tmdBapi = retrofit.create(TMDBService.TMDBapi.class);
        call = tmdBapi.getTrailers(movieId, apiKeyVAlue);
        call.enqueue(new Callback<TmdbTrailersList>() {
            @Override
            public void onResponse(Response<TmdbTrailersList> response) {
                trailerList = response.body();
                trailers = trailerList.getResults();

                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                for (int i = 0; i < trailers.size(); i++) {
                    View v = inflater.inflate(R.layout.trailer_item, null);
                    final TmdbTrailersList.TmdbTrailer t = trailers.get(i);
                    final TextView textView = (TextView) v.findViewById(R.id.trailer_text);
                    ImageView imageView = (ImageView) v.findViewById(R.id.trailer_icon);

                    textView.setText("Trailer " + Integer.toString((i + 1)));
                    trailerLayout.addView(v);

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri builtUri = Uri.parse(YOUTUBE_BASE_URL).buildUpon()
                                    .appendQueryParameter(YOUTUBE_KEY, t.getKey())
                                    .build();
                            Log.d("log", builtUri.toString());
                            Intent intent = new Intent(Intent.ACTION_VIEW, builtUri);
                            startActivity(intent);
                        }
                    });

                }

            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void addReviews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDBService.TMDBapi tmdBapi = retrofit.create(TMDBService.TMDBapi.class);

        Call<TmdbReviewList> call = tmdBapi.getReviews(movieId, apiKeyVAlue);
        call.enqueue(new Callback<TmdbReviewList>() {
            @Override
            public void onResponse(Response<TmdbReviewList> response) {
                reviewList = response.body();
                reviews = reviewList.getResults();

                if (reviews.size() == 0) {
                    Toast.makeText(getContext(), "No reviews for this movie", Toast.LENGTH_SHORT).show();
                } else {

                    for (int i = 0; i < reviews.size(); i++) {
                        TmdbReviewList.TmdbReview review = reviews.get(i);

                        //get author
                        TextView tvAuthor = new TextView(getContext());
                        tvAuthor.setText("Author: " + review.getAuthor());
                        tvAuthor.setTypeface(Typeface.DEFAULT_BOLD);
                        reviewLayout.addView(tvAuthor);

                        //get content
                        TextView tvContent = new TextView(getContext());
                        tvContent.setText(review.getContent());
                        reviewLayout.addView(tvContent);

                        //add seperator
                        TextView seperator = new TextView(getContext());
                        seperator.setText("****");
                        seperator.setPadding(0, 8, 0, 8);
                        seperator.setGravity(Gravity.CENTER);
                        reviewLayout.addView(seperator);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private String dateToYear(String release_date) {
        String releaseDateYear = release_date.trim();
        releaseDateYear = releaseDateYear.substring(0, 4);
        return releaseDateYear;
    }

    /*public String getVoteAverage(String voteAverage) {
        String voteAverageWithTotal = voteAverage + "/10";
        return voteAverageWithTotal;
    }*/

    private void removeFromFavorite() {
        getActivity().getContentResolver().delete(FavoriteMovieProvider.FavoriteMovies.withId(movieIdLong),
                null, null);
        markFavoriteBtn.setText(R.string.mark_as_favorite);
        isFavorite = false;
    }

    public void favoriteBtn(){
        if(!loadFromDB) {
            saveFavoritePoster();
        }
        String posterFilePath = Environment.getExternalStorageDirectory().getPath()
                + "/Popular Movies/" + "f" + movieId + ".jpg";

        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                FavoriteMovieProvider.FavoriteMovies.CONTENT_URI);
        builder.withValue(FavoriteMovieColumns.MOVIE_ID, movieId);
        builder.withValue(FavoriteMovieColumns.ORIGINAL_TITLE, mTitleTextView.getText().toString());
        builder.withValue(FavoriteMovieColumns.OVERVIEW, mOverviewTextView.getText().toString());
        builder.withValue(FavoriteMovieColumns.POSTER, posterFilePath);
        builder.withValue(FavoriteMovieColumns.RELEASEYEAR, mReleasedateTextView.getText().toString());
        builder.withValue(FavoriteMovieColumns.RATING, mRatingTextView.getText().toString());
        operations.add(builder.build());

        try {
            getActivity().getContentResolver().applyBatch(FavoriteMovieProvider.AUTHORITY, operations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }

        isFavorite = true;

    }

    private void saveFavoritePoster() {
        Picasso.with(getContext()).load(tmdbMovie.getFullPosterpath()).into(target);
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    File folder = new File(
                            Environment.getExternalStorageDirectory().getPath()
                                    + "/Popular Movies");
                    folder.mkdirs();
                    File file = new File(folder, "f" + movieId + ".jpg");
                    Log.d(LOG_TAG, file.getPath().toString());
                    try {
                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);
                        boolean saved = bitmap.compress(Bitmap.CompressFormat.JPEG,100,ostream);
                        ostream.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        movieIdLong = Long.parseLong(movieId);
        return new CursorLoader(getActivity(), FavoriteMovieProvider.FavoriteMovies.withId(movieIdLong),
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Log.d(LOG_TAG, "onLoadFinished called");


        if(data != null && data.moveToFirst()) {
            if (loadFromDB) {
                mTitleTextView.setText(data.getString(data.getColumnIndex(FavoriteMovieColumns.ORIGINAL_TITLE)));
                mImageView.setImageURI(Uri.parse(data.getString(data.getColumnIndex(FavoriteMovieColumns.POSTER))));
                mOverviewTextView.setText(data.getString(data.getColumnIndex(FavoriteMovieColumns.OVERVIEW)));
                String strRating = data.getString(data.getColumnIndex(FavoriteMovieColumns.RATING));
                mRatingTextView.setText(strRating);
                String strYear = dateToYear(data.getString(data.getColumnIndex(FavoriteMovieColumns.RELEASEYEAR)));
                mReleasedateTextView.setText(strYear);
            }
            if (Integer.parseInt(movieId) == Integer.parseInt(data.getString(data.getColumnIndex(FavoriteMovieColumns.MOVIE_ID)))) {
                isFavorite = true;
            }
        }

        if(isFavorite){
            markFavoriteBtn.setText(R.string.remove_favorite);
        }else{
            markFavoriteBtn.setText(R.string.mark_as_favorite);
        }

        markFavoriteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isFavorite){
                        removeFromFavorite();
                    }else {
                        favoriteBtn();
                    }
                }
            });

        /*String array[] = new String[data.getCount()];
        int i = 0;

        data.moveToFirst();
        while (!data.isAfterLast()) {
            int index = data.getColumnIndex(FavoriteMovieColumns.MOVIE_ID);
            array[i] = data.getString(index);
            if(Integer.parseInt(movieId) == Integer.parseInt(array[i])){
                isFavorite = true;
                markFavoriteBtn.setText(R.string.remove_favorite);
                movieIdLong =  Long.parseLong(array[i]);
            }
            i++;
            data.moveToNext();
        }*/
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onPause() {
        call.cancel();
        super.onPause();
    }
}
