package com.example.heurion.threadingnothreading;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Bitmap mbitmap;
    private ImageView mIView;
    private int mDelay = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIView = (ImageView) findViewById(R.id.imageView);

        final Button loadButton = (Button) findViewById(R.id.loadButton);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadIcon();
                if (null != mbitmap) {
                    mIView.setImageBitmap(mbitmap);
                }
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

    private void loadIcon(){
        try{
            Thread.sleep(mDelay);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        mbitmap = BitmapFactory.decodeResource(getResources(),R.drawable.painter);
    }




}