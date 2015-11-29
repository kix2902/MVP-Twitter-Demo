package com.redinput.mvptwitterdemo.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("screen_name")
    @Expose
    private String screenName;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The screenName
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     * @param screenName The screen_name
     */
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

}