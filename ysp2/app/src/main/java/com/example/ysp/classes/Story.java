package com.example.ysp.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Story   implements Serializable {

    @SerializedName("storyId")
    @Expose
    private String storyId;
    @SerializedName("storyCraUId")
    @Expose
    private String storyCraUId;
    @SerializedName("storyTitle")
    @Expose
    private String storyTitle;
    @SerializedName("storyText")
    @Expose
    private String storyText;
    @SerializedName("storyCraDate")
    @Expose
    private String storyCraDate;

    public User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Story(String storiesID, User user, String text, String title, String date) {
        this.storyId = storiesID;
        this.storyCraUId = String.valueOf(user.getUserId());
        this.user =user;
        this.storyTitle = title;
        this.storyText = text;
        this.storyCraDate = date;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getStoryCraUId() {
        return storyCraUId;
    }

    public void setStoryCraUId(String storyCraUId) {
        this.storyCraUId = storyCraUId;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryText() {
        return storyText;
    }

    public void setStoryText(String storyText) {
        this.storyText = storyText;
    }

    public String getStoryCraDate() {
        return storyCraDate;
    }

    public void setStoryCraDate(String storyCraDate) {
        this.storyCraDate = storyCraDate;
    }

}
