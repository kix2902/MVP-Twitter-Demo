package com.redinput.mvptwitterdemo.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.redinput.mvptwitterdemo.R;
import com.redinput.mvptwitterdemo.databinding.RowTweetSearchBinding;
import com.redinput.mvptwitterdemo.model.entities.Status;
import com.redinput.mvptwitterdemo.view.binding.TweetRowModel;

import java.util.ArrayList;
import java.util.List;

public class TwitterSearchAdapter extends RecyclerView.Adapter<TwitterSearchAdapter.BindingHolder> {

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private RowTweetSearchBinding binding;

        public BindingHolder(RowTweetSearchBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }
    }

    private Context mContext;
    private List<Status> mTweets;

    public TwitterSearchAdapter(Context context) {
        this.mContext = context;
        this.mTweets = new ArrayList<>();
    }

    @Override
    public TwitterSearchAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowTweetSearchBinding tweetBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_tweet_search,
                parent,
                false);
        return new BindingHolder(tweetBinding);
    }

    @Override
    public void onBindViewHolder(TwitterSearchAdapter.BindingHolder holder, int position) {
        RowTweetSearchBinding tweetBinding = holder.binding;
        tweetBinding.setViewModel(new TweetRowModel(mContext, mTweets.get(position)));
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public void setTweets(List<Status> tweets) {
        mTweets = tweets;
        notifyDataSetChanged();
    }

    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }
}
