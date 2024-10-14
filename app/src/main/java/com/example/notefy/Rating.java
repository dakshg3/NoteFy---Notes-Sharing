package com.example.notefy;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Rating implements Serializable {
    private String mFileName;
    private String mAuthor;
    private double mRating;
    private String mKey;


    public Rating() {
        //empty constructor needed
    }

    public Rating(String name, String author, double rating) {
        mFileName=name;
        mAuthor=author;
        mRating=rating;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String name) {
        mFileName = name;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public double getRating() { return mRating; }

    public void setRating(Double rating) {
        mRating = rating;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}