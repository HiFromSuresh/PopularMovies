package com.sureshssk2006.gmail.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private static final String YOUTUBE_KEY = "v";
    final String BASE_URL = "https://api.themoviedb.org/";
    final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?";
    Call<TmdbTrailersList> call;
    private String apiKeyVAlue;
    LinearLayout trailerLayout;
    LinearLayout reviewLayout;
    TextView reviewBtn;

    private final String OBJECT_KEY = "object_key";
    private TextView mTitleTextView;
    private ImageView mImageView;
    private TextView mOverviewTextView;
    private TextView mReleasedateTextView;
    private TextView mRatingTextView;
    TMDBmovieList.TmdbMovee tmdbMovie;
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


        //get Data from bundle
        Bundle bundle = this.getArguments();
        tmdbMovie = bundle.getParcelable(OBJECT_KEY);

        mTitleTextView.setText(tmdbMovie.getOriginal_title());
        Picasso.with(getContext()).load(tmdbMovie.getFullPosterpath()).into(mImageView);
        mOverviewTextView.setText(tmdbMovie.getOverview());
        mRatingTextView.setText(getVoteAverage(tmdbMovie.getVote_average()));
        mReleasedateTextView.setText(dateToYear(tmdbMovie.getRelease_date()));

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
        call = tmdBapi.getTrailers(tmdbMovie.getId(), apiKeyVAlue);
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
                Toast.makeText(getContext(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addReviews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDBService.TMDBapi tmdBapi = retrofit.create(TMDBService.TMDBapi.class);

        Call<TmdbReviewList> call = tmdBapi.getReviews(tmdbMovie.getId(), apiKeyVAlue);
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

    public String getVoteAverage(String voteAverage) {
        String voteAverageWithTotal = voteAverage + "/10";
        return voteAverageWithTotal;
    }

}
