package com.redinput.mvptwitterdemo.model;

public interface GetTwitterApiUsecase {

    void getRequestUrl();

    void getTokenData(String verification);

    void searchTweets(String query);

}
