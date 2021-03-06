
package com.example.ysp.classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnStory {

    @SerializedName("stories")
    @Expose
    private List<Story> stories = null;

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

}
