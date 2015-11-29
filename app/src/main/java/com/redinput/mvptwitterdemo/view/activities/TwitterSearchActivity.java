package com.redinput.mvptwitterdemo.view.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.redinput.mvptwitterdemo.R;
import com.redinput.mvptwitterdemo.model.entities.Status;
import com.redinput.mvptwitterdemo.presenter.TwitterSearchPresenter;
import com.redinput.mvptwitterdemo.presenter.TwitterSearchPresenterImpl;
import com.redinput.mvptwitterdemo.view.adapters.TwitterSearchAdapter;
import com.redinput.mvptwitterdemo.view.views.TwitterSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TwitterSearchActivity extends AppCompatActivity implements TwitterSearchView {

    private TwitterSearchPresenter mTwitterSearchPresenter;
    private TwitterSearchAdapter mAdapter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.search_view)
    MaterialSearchView mSearchView;

    @Bind(R.id.activity_main_content)
    FrameLayout mContentLayout;

    @Bind(R.id.activity_main_recycler)
    RecyclerView mRecycler;

    @Bind(R.id.activity_main_search_hint)
    TextView mSearchHint;

    @Bind(R.id.activity_main_loading)
    ProgressBar mLoading;

    @Bind(R.id.activity_main_webview)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_search);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("oauth")) {
                    Uri uri = Uri.parse(url);
                    mTwitterSearchPresenter.performVerifyAccessToken(uri);

                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mTwitterSearchPresenter = new TwitterSearchPresenterImpl(TwitterSearchActivity.this);
        mTwitterSearchPresenter.performRequestUrl();

        mAdapter = new TwitterSearchAdapter(getContext());
        mRecycler.setAdapter(mAdapter);

        mSearchView.setVoiceSearch(true);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mTwitterSearchPresenter.performSearch(query);
                getSupportActionBar().setSubtitle(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_twitter_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    mSearchView.setQuery(searchWrd, true);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showSearchResult(List<Status> tweets) {
        mAdapter.setTweets(tweets);
    }

    @Override
    public void showWebView(String url) {
        mWebView.setVisibility(View.VISIBLE);
        mWebView.loadUrl(url);
    }

    @Override
    public void hideWebView() {
        mWebView.setVisibility(View.GONE);
    }

    @Override
    public void showSearchHint() {
        mSearchHint.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSearchHint() {
        mSearchHint.setVisibility(View.GONE);
    }

    @Override
    public void clearResults() {
        mAdapter.clear();
    }

    @Override
    public void showLoading() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mContentLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return TwitterSearchActivity.this;
    }
}
