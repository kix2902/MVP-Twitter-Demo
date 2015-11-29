package com.redinput.mvptwitterdemo.view.binding;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;
import android.widget.Toast;

import com.redinput.mvptwitterdemo.R;
import com.redinput.mvptwitterdemo.model.entities.Status;

public class TweetRowModel extends BaseObservable {

    private Context mContext;
    private Status tweet;

    public TweetRowModel(Context context, Status tweet) {
        this.tweet = tweet;
        this.mContext = context;
    }

    public String getTweetAuthor() {
        return "@" + tweet.getUser().getScreenName();
    }

    public String getTweetText() {
        return tweet.getText();
    }

    public String getTweetFavorites() {
        return String.valueOf(tweet.getFavoriteCount());
    }

    public String getTweetRetweets() {
        return String.valueOf(tweet.getRetweetCount());
    }

    public View.OnClickListener onClickTweet() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mContext.getString(R.string.onclick_row_toast_text, tweet.getIdStr()), Toast.LENGTH_SHORT).show();
            }
        };
    }
}
