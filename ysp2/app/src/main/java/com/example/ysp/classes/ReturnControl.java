
package com.example.ysp.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnControl {

    @SerializedName("control")
    @Expose
    private String control;

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

}
