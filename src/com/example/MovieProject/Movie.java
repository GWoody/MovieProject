package com.example.MovieProject;

import android.graphics.Bitmap;

/**
 * Created by Gareth on 14/10/2014.
 */
public class Movie {

    private String title;
    private String year;
    private String rating;
    private String criticScore;
    private String runTime;
    private String synopsis;
    private Bitmap thumbnail;
    private Bitmap image;

    public Movie(String title, String year, String rating, String criticScore, String runTime, String synopsis, Bitmap thumbnail, Bitmap image) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.criticScore = criticScore;
        this.runTime = runTime;
        this.synopsis = synopsis;
        this.thumbnail = thumbnail;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getCriticScore() {
        return criticScore;
    }

    public String getRunTime() {
        return runTime;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public Bitmap getImage() {
        return image;
    }
}
