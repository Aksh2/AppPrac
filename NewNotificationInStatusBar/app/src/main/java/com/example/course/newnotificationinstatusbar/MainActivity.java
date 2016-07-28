package com.example.course.newnotificationinstatusbar;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    // Notification ID to allow for future updates
    private static final int MY_NOTIFICATIONS_ID = 1;

   // Notification Count
    private int mNotificationCount;

    // Notification Text Elements
    private final CharSequence tickerText = "This is a Really, Super Long Notification Message!";
    private final CharSequence contentText = "You've Been Notified!";

    // Notification Action Elements
    private Intent mNotificationIntent;
    private PendingIntent mContentIntent;

    private Uri soundURI = Uri
            .parse("android.resource://com.example.course.newnotificationinstatusbar/"+
                   R.raw.alarm_rooster);

    private long[] mVibratePattern ={0, 200, 200, 300};

    RemoteViews mContentView = new RemoteViews("com.example.course.newnotificationinstatusbar",
            R.layout.custom_notification);

    // Notification Action Elements

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationIntent = new Intent(getApplicationContext(),SubActivity.class);

        mContentIntent = PendingIntent.getActivity(getApplicationContext(),0,
                mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                mContentView.setTextViewText(R.id.text,contentText+"("+ ++mNotificationCount+")");

                Notification.Builder notificationBuilder = new Notification.Builder(
                        getApplicationContext())
                        .setTicker(tickerText)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setAutoCancel(true)
                        .setContentIntent(mContentIntent)
                        .setSound(soundURI)
                        .setVibrate(mVibratePattern)
                        .setContent(mContentView);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(MY_NOTIFICATIONS_ID,
                        notificationBuilder.build());

            }
        });
    }
}
