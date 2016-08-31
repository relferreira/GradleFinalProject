package com.udacity.gradle.builditbigger.free;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.JokeEndpointAsyncTask;
import com.udacity.gradle.builditbigger.R;
import com.udacity.jokeviewlibrary.JokeViewActivity;


public class MainActivity extends ActionBarActivity implements JokeEndpointAsyncTask.JokeCallback{

    InterstitialAd interstitialAd;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = (ProgressBar) findViewById(R.id.loading);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                showJoke();
            }
        });

        requestNewInterstitial();
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
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            showJoke();
        }

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        interstitialAd.loadAd(adRequest);
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
