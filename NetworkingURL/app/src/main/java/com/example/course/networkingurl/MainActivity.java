package com.example.course.networkingurl;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

    private class HttpGetTask extends AsyncTask<Void,Void,String> {
        private static final String TAG = "HttpGetTask";

        private static final String USER_NAME="akshay123";
        private static final String URL = "http://api.geonames.org/earthquakesJSON?north=44.1&south=-9.9&east=-22.4&west=55&username="
                        + USER_NAME;

        @Override
        protected String doInBackground(Void... params){
            String data = "";
            HttpURLConnection httpUrlConnection = null;

            try{
                httpUrlConnection = (HttpURLConnection) new URL(URL)
                        .openConnection();

                InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());

                data = readStream(in);
            }catch (MalformedURLException exception){
                Log.e(TAG,"MalformedURLException");
            }catch (IOException exception){
                Log.e(TAG,"IOException");
            }finally{
                if(null!= httpUrlConnection)
                    httpUrlConnection.disconnect();
            }
            return data;
            }

        @Override
        protected void onPostExecute(String result){
            mTextView.setText(result);

        }

        private String readStream(InputStream in){
            BufferedReader reader = null;
            StringBuffer data = new StringBuffer("");

            try{
                reader = new BufferedReader(new InputStreamReader(in));
                String line ="";
                while((line = reader.readLine())!=null){
                    data.append(line);
                }
              }catch (IOException e) {
                Log.e(TAG,"IOException");
            }finally {
                if(reader != null){
                    try{
                        reader.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return data.toString();
        }


    }
}
