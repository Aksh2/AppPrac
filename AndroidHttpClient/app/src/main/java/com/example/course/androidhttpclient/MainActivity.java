package com.example.course.androidhttpclient;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView1);

        final Button loadButton = (Button) findViewById(R.id.button1);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpGetTask().execute();
            }
        });
    }

    private class HttpGetTask extends AsyncTask<Void,Void,String>{

        private static final String USER_NAME="akshay123";

        private static final String URL = "http://api.geonames.org/earthquakesJSON?north=44.1&south=-9.9&east=-22.4&west=55&username="
                + USER_NAME;

        AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

        @Override
        protected String doInBackground(Void... voids) {
            HttpGet request = new HttpGet(URL);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            try {
                return mClient.execute(request,responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override

        protected void onPostExecute(String result){

            if(null!=mClient)
                mClient.close();

            mTextView.setText(result);
        }
    }




}
