package com.redinput.mvptwitterdemo.view.views;

import com.redinput.mvptwitterdemo.model.entities.Status;

import java.util.List;

public interface TwitterSearchView extends BaseView {

    void showLoading();

    void hideLoading();

    void showWebView(String url);

    void hideWebView();

    void showSearchHint();

    void hideSearchHint();

    void showSearchResult(List<Status> tweets);

    void clearResults();

    void showError(String message);

}