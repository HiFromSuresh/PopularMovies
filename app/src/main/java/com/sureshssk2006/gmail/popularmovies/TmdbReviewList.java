package com.sureshssk2006.gmail.popularmovies;

import java.util.List;

/**
 * Created by Administrator on 3/30/2016.
 */
public class TmdbReviewList {

    private List<TmdbReview> results;

    public List<TmdbReview> getResults() {
        return results;
    }

    public void setResults(List<TmdbReview> results) {
        this.results = results;
    }

    public class TmdbReview {
        String author;
        String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
