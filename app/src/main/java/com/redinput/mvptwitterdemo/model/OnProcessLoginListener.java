package com.redinput.mvptwitterdemo.model;

public interface OnProcessLoginListener {

    void onRequestUrlResult(String url);

    void onRequestTokenResult(String token, String secret);

    void onError(String message);
}
