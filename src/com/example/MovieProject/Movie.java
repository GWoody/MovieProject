package com.example.MovieProject;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gareth on 14/10/2014.
 */
public class Movie implements Parcelable {

    private String title;
    private String year;
    private String rating;
    private String criticScore;
    private String runTime;
    private String synopsis;
    private Bitmap thumbnail;
    private Bitmap image;

    public Movie(Parcel in) {
        this.title = in.readString();
        this.year = in.readString();
        this.rating = in.readString();
        this.criticScore = in.readString();
        this.runTime = in.readString();
        this.synopsis = in.readString();
        this.thumbnail = in.readParcelable(Bitmap.class.getClassLoader());
        this.image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public Movie(String title, String year, String rating, String criticScore, String runTime, String synopsis) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.criticScore = criticScore;
        this.runTime = runTime;
        this.synopsis = synopsis;
        this.thumbnail = null;
        this.image = null;
    }

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

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.year);
        dest.writeString(this.rating);
        dest.writeString(this.criticScore);
        dest.writeString(this.runTime);
        dest.writeString(this.synopsis);
        dest.writeParcelable(this.thumbnail, flags);
        dest.writeParcelable(this.image, flags);
    }
}