package com.example.course.threadinghandlingrunnable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Bitmap mBitmap;
    private ImageView mIView;
    private ProgressBar mProgressBar;
    private int mDelay = 500;
    private final Handler handler = new Handler();

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
                new Thread(new LoadIconTask(R.drawable.painter)).start();

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

    private class LoadIconTask implements Runnable{

        int resId;

        LoadIconTask(int resId){
            this.resId=resId;
        }

        @Override
        public void run() {

            handler.post(new Runnable(){

                @Override
                public void run() {
                    mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            mBitmap = BitmapFactory.decodeResource(getResources(),resId);

            for (int i=1;i<11;i++){
                sleep();
                final int step = i;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                     mProgressBar.setProgress(step*10);
                    }
                });
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    mIView.setImageBitmap(mBitmap);
                }
            });

            handler.post(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            });

        }
    }

    private void sleep(){
        try{
            Thread.sleep(mDelay);

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
