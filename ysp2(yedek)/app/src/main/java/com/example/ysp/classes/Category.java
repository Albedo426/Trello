package com.example.ysp.classes;

import android.util.Log;
import android.widget.EditText;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

public class Category implements Serializable  {

    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("storiesId")
    @Expose
    private String storiesId;
    @SerializedName("categoryType")
    @Expose
    private String categoryType;
    @SerializedName("FinishDate")
    @Expose
    private String finishDate;
    @SerializedName("categoryText")
    @Expose
    private String categoryText;
    @SerializedName("categoryTitle")
    @Expose
    private String categoryTitle;
    @SerializedName("categoryResUId")
    @Expose
    private String categoryResUId;

    private Story stories;
    private User resuser;


    public Story getStories() {
        return stories;
    }

    public void setStories(Story stories) {
        this.stories = stories;
    }

    public User getResuser() {
        return resuser;
    }

    public void setResuser(User resuser) {
        this.resuser = resuser;
    }

    public Category(String kategoriesID, Story stories, String type, String finishDate, String text, String title, User resuser) {
        this.categoryId = kategoriesID;
        this.storiesId = String.valueOf(stories.getStoryId());
        this.stories = stories;
        this.categoryType = type;
        this.finishDate = finishDate;
        this.categoryText = text;
        this.categoryTitle = title;
        this.categoryResUId = String.valueOf(resuser.getUserId());
        this.resuser = resuser;

    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStoriesId() {
        return storiesId;
    }

    public void setStoriesId(String storiesId) {
        this.storiesId = storiesId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryResUId() {
        return categoryResUId;
    }

    public void setCategoryResUId(String categoryResUId) {
        this.categoryResUId = categoryResUId;
    }


}
