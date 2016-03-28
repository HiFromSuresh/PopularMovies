package com.sureshssk2006.gmail.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 3/22/2016.
 */
public class TMDBmovieList {

    public List<TmdbMovee> getResults() {
        return results;
    }

    public void setResults(List<TmdbMovee> results) {
        this.results = results;
    }

    private List<TmdbMovee> results;


    public static class TmdbMovee implements Parcelable {

        String results;
        String original_title;
        String poster_path;
        String overview;
        String release_date;
        String vote_average;
        String id;


        protected TmdbMovee(Parcel in) {
            results = in.readString();
            original_title = in.readString();
            poster_path = in.readString();
            overview = in.readString();
            release_date = in.readString();
            vote_average = in.readString();
            id = in.readString();
            fullPosterpath = in.readString();
        }

        public static final Creator<TmdbMovee> CREATOR = new Creator<TmdbMovee>() {
            @Override
            public TmdbMovee createFromParcel(Parcel in) {
                return new TmdbMovee(in);
            }

            @Override
            public TmdbMovee[] newArray(int size) {
                return new TmdbMovee[size];
            }
        };

        public String getFullPosterpath() {
            return fullPosterpath;
        }

        public void setFullPosterpath(String fullPosterpath) {
            this.fullPosterpath = fullPosterpath;
        }

        String fullPosterpath;

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public String getVote_average() {
            return vote_average;
        }

        public void setVote_average(String vote_average) {
            this.vote_average = vote_average;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getResults() {
            return results;
        }

        public void setResults(String results) {
            this.results = results;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(results);
            dest.writeString(original_title);
            dest.writeString(poster_path);
            dest.writeString(overview);
            dest.writeString(release_date);
            dest.writeString(vote_average);
            dest.writeString(id);
            dest.writeString(fullPosterpath);
        }
    }
}
