package com.redinput.mvptwitterdemo.model;

import android.os.Handler;

import com.redinput.mvptwitterdemo.data.DataSource;
import com.redinput.mvptwitterdemo.model.entities.SearchResult;
import com.redinput.mvptwitterdemo.presenter.TwitterSearchPresenter;

public class GetTwitterApiController implements GetTwitterApiUsecase, OnProcessLoginListener, OnSearchFinishedListener {

    private DataSource mDataSource;
    private TwitterSearchPresenter mTwitterSearchPresenter;

    public GetTwitterApiController(TwitterSearchPresenter twitterSearchPresenter, String token, String secret) {
        this.mTwitterSearchPresenter = twitterSearchPresenter;
        mDataSource = DataSource.getInstance(new Handler(), token, secret, this, this);
    }

    @Override
    public void getRequestUrl() {
        mDataSource.retrieveRequestTokenUrl();
    }

    @Override
    public void onRequestUrlResult(String url) {
        mTwitterSearchPresenter.onRequestUrlReceived(url);
    }

    @Override
    public void getTokenData(String verification) {
        mDataSource.retrieveAccessToken(verification);
    }

    @Override
    public void searchTweets(String query) {
        mDataSource.searchTwitter(query);
    }

    @Override
    public void onRequestTokenResult(String token, String secret) {
        mTwitterSearchPresenter.onAccessTokenVerified(token, secret);
    }

    @Override
    public void onError(String message) {
        mTwitterSearchPresenter.onError(message);
    }

    @Override
    public void onSearchFinished(SearchResult searchResult) {
        mTwitterSearchPresenter.onSearchResultReceived(searchResult.getStatuses());
    }
}
