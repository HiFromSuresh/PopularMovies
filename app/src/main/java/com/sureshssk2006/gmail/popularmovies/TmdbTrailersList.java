package com.sureshssk2006.gmail.popularmovies;

import java.util.List;

/**
 * Created by Administrator on 3/28/2016.
 */
public class TmdbTrailersList {

    public List<TmdbTrailer> getResults() {
        return results;
    }

    public void setResults(List<TmdbTrailer> results) {
        this.results = results;
    }

    private List<TmdbTrailer> results;

    public static class TmdbTrailer {

        String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
