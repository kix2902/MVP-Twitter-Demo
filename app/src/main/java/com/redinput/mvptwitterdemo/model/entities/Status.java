package com.redinput.mvptwitterdemo.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id_str")
    @Expose
    private String idStr;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("retweet_count")
    @Expose
    private Integer retweetCount;
    @SerializedName("favorite_count")
    @Expose
    private Integer favoriteCount;

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The idStr
     */
    public String getIdStr() {
        return idStr;
    }

    /**
     * @param idStr The id_str
     */
    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return The retweetCount
     */
    public Integer getRetweetCount() {
        return retweetCount;
    }

    /**
     * @param retweetCount The retweet_count
     */
    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }

    /**
     * @return The favoriteCount
     */
    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    /**
     * @param favoriteCount The favorite_count
     */
    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

}