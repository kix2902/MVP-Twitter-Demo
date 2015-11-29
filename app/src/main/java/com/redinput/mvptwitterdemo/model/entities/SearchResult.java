package com.redinput.mvptwitterdemo.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

    @SerializedName("statuses")
    @Expose
    private List<Status> statuses = new ArrayList<Status>();
    @SerializedName("search_metadata")
    @Expose
    private SearchMetadata searchMetadata;

    /**
     * @return The statuses
     */
    public List<Status> getStatuses() {
        return statuses;
    }

    /**
     * @param statuses The statuses
     */
    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    /**
     * @return The searchMetadata
     */
    public SearchMetadata getSearchMetadata() {
        return searchMetadata;
    }

    /**
     * @param searchMetadata The search_metadata
     */
    public void setSearchMetadata(SearchMetadata searchMetadata) {
        this.searchMetadata = searchMetadata;
    }

}