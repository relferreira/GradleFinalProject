package com.udacity.gradle.builditbigger.paid;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.udacity.gradle.builditbigger.JokeEndpointAsyncTask;
import com.udacity.gradle.builditbigger.R;
import com.udacity.jokeviewlibrary.JokeViewActivity;


public class MainActivity extends ActionBarActivity implements JokeEndpointAsyncTask.JokeCallback{


    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = (ProgressBar) findViewById(R.id.loading);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        showJoke();
    }

    private void showJoke() {
        showLoading(true);
        JokeEndpointAsyncTask task = new JokeEndpointAsyncTask();
        task.execute(this);
    }

    @Override
    public String getBackendUrl() {
        return getString(R.string.backend_url);
    }

    @Override
    public void showLoading(boolean state) {
        loading.setVisibility((state) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showJoke(String joke) {
        Intent intent = new Intent(this, JokeViewActivity.class);
        intent.putExtra(JokeViewActivity.ARG_JOKE, joke);
        startActivity(intent);
    }
}
