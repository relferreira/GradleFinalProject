package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.backend.myApi.MyApi;

import java.io.IOException;


public class JokeEndpointAsyncTask extends AsyncTask<JokeEndpointAsyncTask.JokeCallback, Void, String> {

    private static MyApi myApiService = null;
    private JokeCallback calback;

    public interface JokeCallback{
        String getBackendUrl();
        void showLoading(boolean state);
        void showJoke(String joke);
    }

    @Override
    protected String doInBackground(JokeCallback... params) {
        calback = params[0];

        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(calback.getBackendUrl())
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });


            myApiService = builder.build();
        }

        try {
            return myApiService.jokes().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        calback.showLoading(false);
        calback.showJoke(result);
    }
}