package com.redinput.mvptwitterdemo.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchMetadata {

    @SerializedName("count")
    @Expose
    private Integer count;

    /**
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

}