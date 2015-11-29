package com.redinput.mvptwitterdemo.presenter;

import android.net.Uri;

import com.redinput.mvptwitterdemo.model.entities.Status;

import java.util.List;

public interface TwitterSearchPresenter {

    void performRequestUrl();

    void onRequestUrlReceived(String url);

    void performVerifyAccessToken(Uri uri);

    void onAccessTokenVerified(String token, String secret);

    void performSearch(String query);

    void onSearchResultReceived(List<Status> tweets);

    void onError(String message);

}
