package com.redinput.mvptwitterdemo.presenter;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.redinput.mvptwitterdemo.model.GetTwitterApiController;
import com.redinput.mvptwitterdemo.model.GetTwitterApiUsecase;
import com.redinput.mvptwitterdemo.model.entities.Status;
import com.redinput.mvptwitterdemo.util.Config;
import com.redinput.mvptwitterdemo.view.views.TwitterSearchView;

import java.util.List;

public class TwitterSearchPresenterImpl implements TwitterSearchPresenter {

    private TwitterSearchView mTwitterSearchView;
    private SharedPreferences mSharedPreferences;

    private GetTwitterApiUsecase mGetTwitterApiUsecase;

    public TwitterSearchPresenterImpl(TwitterSearchView twitterSearchView) {
        this.mTwitterSearchView = twitterSearchView;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(twitterSearchView.getContext());
    }

    @Override
    public void performRequestUrl() {
        mTwitterSearchView.showLoading();

        String token = mSharedPreferences.getString(Config.OAUTH_TOKEN_PREFERENCE, null);
        String secret = mSharedPreferences.getString(Config.OAUTH_TOKEN_SECRET_PREFERENCE, null);
        mGetTwitterApiUsecase = new GetTwitterApiController(this, token, secret);

        if ((token == null) && (secret == null)) {
            mGetTwitterApiUsecase.getRequestUrl();

        } else {
            mTwitterSearchView.hideLoading();
            mTwitterSearchView.showSearchHint();
        }
    }

    @Override
    public void onRequestUrlReceived(String url) {
        mTwitterSearchView.hideLoading();
        mTwitterSearchView.showWebView(url);
    }

    @Override
    public void performVerifyAccessToken(Uri uri) {
        mTwitterSearchView.showLoading();
        mTwitterSearchView.hideWebView();

        String verification = uri.getQueryParameter("oauth_verifier");
        mGetTwitterApiUsecase.getTokenData(verification);
    }

    @Override
    public void onAccessTokenVerified(String token, String secret) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Config.OAUTH_TOKEN_PREFERENCE, token);
        editor.putString(Config.OAUTH_TOKEN_SECRET_PREFERENCE, secret);
        editor.apply();

        mTwitterSearchView.hideLoading();
        mTwitterSearchView.showSearchHint();
        mTwitterSearchView.clearResults();
    }

    @Override
    public void performSearch(String query) {
        mTwitterSearchView.showLoading();
        mTwitterSearchView.hideSearchHint();

        mGetTwitterApiUsecase.searchTweets(query);
    }

    @Override
    public void onSearchResultReceived(List<Status> tweets) {
        mTwitterSearchView.hideLoading();
        mTwitterSearchView.showSearchResult(tweets);
    }

    @Override
    public void onError(String message) {
        mTwitterSearchView.hideLoading();
        mTwitterSearchView.showError(message);
    }
}
