package com.redinput.mvptwitterdemo.data;

import android.os.Handler;
import android.text.TextUtils;

import com.redinput.mvptwitterdemo.model.OnProcessLoginListener;
import com.redinput.mvptwitterdemo.model.OnSearchFinishedListener;
import com.redinput.mvptwitterdemo.model.entities.SearchResult;
import com.redinput.mvptwitterdemo.util.Config;
import com.squareup.okhttp.OkHttpClient;

import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class DataSource {

    private static final String SEARCH_TYPE = "recent";

    private static DataSource tweetDataSource;

    private OkHttpOAuthConsumer oAuthConsumer;
    private OAuthProvider oAuthProvider;

    private RetrofitTwitterApi twitterApiClient;

    private OnProcessLoginListener mOnProcessLoginListener;
    private OnSearchFinishedListener mOnSearchFinishedListener;

    private Handler mHandler;

    private DataSource(Handler handler, String token, String secret, OnProcessLoginListener onProcessLoginListener, OnSearchFinishedListener onSearchFinishedListener) {
        oAuthConsumer = new OkHttpOAuthConsumer(
                Config.TWEET_API_KEY,
                Config.TWEET_API_SECRET);

        this.mHandler = handler;
        this.mOnProcessLoginListener = onProcessLoginListener;
        this.mOnSearchFinishedListener = onSearchFinishedListener;

        if ((!TextUtils.isEmpty(token)) && (!TextUtils.isEmpty(secret))) {
            oAuthConsumer.setTokenWithSecret(token, secret);
            createTwitterApiClient();

        } else {
            oAuthProvider = new DefaultOAuthProvider(
                    Config.REQUEST_TOKEN_ENDPOINT,
                    Config.ACCESS_TOKEN_ENDPOINT,
                    Config.AUTHORIZATION_ENDPOINT);

        }
    }

    private void createTwitterApiClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(new SigningInterceptor(oAuthConsumer));

        Retrofit restClient = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        twitterApiClient = restClient.create(RetrofitTwitterApi.class);
    }

    public static DataSource getInstance(Handler handler, String token, String secret, OnProcessLoginListener onProcessLoginListener, OnSearchFinishedListener onSearchFinishedListener) {
        if (tweetDataSource == null) {
            tweetDataSource = new DataSource(handler, token, secret, onProcessLoginListener, onSearchFinishedListener);
        }
        return tweetDataSource;
    }

    public void retrieveRequestTokenUrl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String url = oAuthProvider.retrieveRequestToken(oAuthConsumer, Config.CALLBACK_URL);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mOnProcessLoginListener.onRequestUrlResult(url);
                        }
                    });

                } catch (OAuthMessageSignerException | OAuthCommunicationException | OAuthExpectationFailedException | OAuthNotAuthorizedException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mOnProcessLoginListener.onError(e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }

    public void retrieveAccessToken(final String verificationToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    oAuthProvider.retrieveAccessToken(oAuthConsumer, verificationToken);
                    createTwitterApiClient();

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mOnProcessLoginListener.onRequestTokenResult(oAuthConsumer.getToken(), oAuthConsumer.getTokenSecret());
                        }
                    });

                } catch (OAuthMessageSignerException | OAuthNotAuthorizedException | OAuthCommunicationException | OAuthExpectationFailedException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mOnProcessLoginListener.onError(e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }

    public void searchTwitter(String query) {
        Call<SearchResult> responseCall = twitterApiClient.searchTweets(query, SEARCH_TYPE);
        responseCall.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Response<SearchResult> response, Retrofit retrofit) {
                mOnSearchFinishedListener.onSearchFinished(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                mOnSearchFinishedListener.onError(t.getMessage());
            }
        });

    }
}
