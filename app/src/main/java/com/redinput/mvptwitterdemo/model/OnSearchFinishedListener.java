package com.redinput.mvptwitterdemo.model;

import com.redinput.mvptwitterdemo.model.entities.SearchResult;

public interface OnSearchFinishedListener {

    void onError(String message);

    void onSearchFinished(SearchResult searchResult);

}
