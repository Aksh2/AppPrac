package com.example.course.threadinghandlermessages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private final static int SET_PROGRESS_BAR_VISIBITY = 0;
    private final static int PROGRESS_UPDATE = 1;
    private final static int SET_BITMAP  =2;


    private ImageView mIView;
    private ProgressBar mProgressBar;
    private int mDelay = 500;

    static class UIHandler extends Handler {
        WeakReference<MainActivity> mParent;

        public UIHandler(WeakReference<MainActivity> parent){
            mParent = parent;
        }

        @Override
        public void handleMessage(Message msg){

            MainActivity parent = mParent.get();

            if(null!=parent){
                switch (msg.what){
                    case SET_PROGRESS_BAR_VISIBITY:{
                        parent.getProgressBar().setVisibility((Integer) msg.obj);
                        break;
                    }
                    case PROGRESS_UPDATE:{
                        parent.getProgressBar().setProgress((Integer) msg.obj);
                        break;
                    }
                    case SET_BITMAP:{
                        parent.getImageView().setImageBitmap((Bitmap) msg.obj);
                        break;
                    }
                }
            }

        }
    }

    Handler handler = new UIHandler(new WeakReference<MainActivity>(this));



    public ProgressBar getProgressBar() {
    return mProgressBar;
    }

    public ImageView getImageView() {
        return mIView;
    }

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
                new Thread(new LoadIconTask(R.drawable.painter,handler)).start();

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
        private final int resID;
        private final Handler handler;


        LoadIconTask(int resID, Handler handler){
            this.resID = resID;
            this.handler =handler;
        }

        @Override
        public void run() {

            Message msg = handler.obtainMessage(SET_PROGRESS_BAR_VISIBITY,ProgressBar.VISIBLE);
            handler.sendMessage(msg);

            final Bitmap tmp = BitmapFactory.decodeResource(getResources(),resID);

            for(int i=1;i<11;i++){
                sleep();
                msg =handler.obtainMessage(PROGRESS_UPDATE,i*10);
                handler.sendMessage(msg);
            }

            msg = handler.obtainMessage(SET_BITMAP,tmp);
            handler.sendMessage(msg);

            msg = handler.obtainMessage(SET_PROGRESS_BAR_VISIBITY,ProgressBar.INVISIBLE);
            handler.sendMessage(msg);
        }

        private void sleep(){
            try{
                Thread.sleep(mDelay);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
