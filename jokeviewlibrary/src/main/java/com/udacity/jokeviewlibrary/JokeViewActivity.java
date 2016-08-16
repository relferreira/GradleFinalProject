package com.udacity.jokeviewlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by relferreira on 8/15/16.
 */
public class JokeViewActivity extends AppCompatActivity {

    public static final String ARG_JOKE = "arg_joke";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        TextView jokeView = (TextView) findViewById(R.id.joke_text);

        if(getIntent().hasExtra(ARG_JOKE)) {
            String joke = getIntent().getStringExtra(ARG_JOKE);
            jokeView.setText(joke);
        }
    }
}
