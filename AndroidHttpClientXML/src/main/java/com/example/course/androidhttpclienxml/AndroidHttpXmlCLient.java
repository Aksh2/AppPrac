package com.example.course.androidhttpclienxml;

import android.app.ListActivity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.List;

/**
 * Created by heurion on 26/7/16.
 */
public class AndroidHttpXmlCLient extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        new HttpGetTask().execute();
    }

    private class HttpGetTask extends AsyncTask<Void,Void,List<String>>{

        private static final  String USER_NAME = "aporter";

        private  static final String URL = "http://api.geonames.org/earthquakes?north=44.1&south=-9.9&east=-22&west=55.2&username="
                + USER_NAME;

        AndroidHttpClient mClient = AndroidHttpClient.newInstance("");
        @Override
        protected List<String> doInBackground(Void... params){
            HttpGet request = new HttpGet(URL);
            XMLResponseHandler responseHandler = new XMLResponseHandler();

            try{
                return mClient.execute(request,responseHandler);
            }
            catch (ClientProtocolException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);

            if(null!=mClient)
            mClient.close();
            setListAdapter(new ArrayAdapter<String>(AndroidHttpXmlCLient.this,R.layout.list_item,result));
        }
    }

}
