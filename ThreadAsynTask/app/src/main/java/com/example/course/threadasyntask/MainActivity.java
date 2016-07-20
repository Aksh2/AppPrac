package com.example.course.threadasyntask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "ThreadingAsyncTask";


    private ImageView mIView;
    private ProgressBar mProgressBar;
    private int mDelay = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIView = (ImageView) findViewById(R.id.imageView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        final Button loadButton = (Button) findViewById(R.id.loadButton);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadIconTask().execute(R.drawable.painter);

            }
        });

        final Button otherButton = (Button) findViewById(R.id.otherButton);
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"I'm Working",Toast.LENGTH_SHORT).show();
            }
        });
    }

    class  LoadIconTask extends AsyncTask<Integer,Integer,Bitmap>{
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Integer... resId) {
           Bitmap tmp = BitmapFactory.decodeResource(getResources(),resId[0]);

            for(int i=1;i<11;i++){
                sleep();
                publishProgress(i*10);
            }
            return tmp;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            mIView.setImageBitmap(result);
        }

        private void sleep(){
            try{
                Thread.sleep(mDelay);
            }catch (InterruptedException e){
                Log.e(TAG,e.toString());
            }

        }

    }
}
