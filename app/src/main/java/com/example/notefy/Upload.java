package com.example.notefy;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Upload implements Serializable {
    private String mName;
    private String mCourse;
    private String mFileUrl;
    private String mSem;
    private String mDesc;
    private String mKey;
    private String mAuthor;


    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String fileUrl, String course, String sem, String desc,String author) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mCourse=course;
        mName = name;
        mFileUrl = fileUrl;
        mDesc=desc;
        mSem=sem;
        mAuthor=author;

    }

    public String getName() {
        return mName;
    }

    public String getCourse() {
        return mCourse;
    }

    public String getDesc() {
        return mDesc;
    }

    public String getSem() {
        return mSem;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setCourse(String course) {
        mCourse = course;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public void setSem(String sem) {
        mSem = sem;
    }

    public String getfileUrl() {
        return mFileUrl;
    }

    public void setAuthor(String author) { mAuthor = author;    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setfileUrl(String fileUrl) {
        mFileUrl = fileUrl;
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