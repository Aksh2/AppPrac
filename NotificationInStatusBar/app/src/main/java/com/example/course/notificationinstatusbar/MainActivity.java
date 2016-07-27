package com.example.course.notificationinstatusbar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Notification ID for future updates
    private static final int MY_NOTIFICATION_ID=1;

    // notification Count
    private  int mNotificationCount;

    // Notification Text Elements

    private final CharSequence tickerText = "This is Really, Really, Super Long Notification Message";
    private final CharSequence contentTitle="Notification";
    private final CharSequence contentText="You've been notified!";

    private Intent mNotificationIntent;
    private PendingIntent mContentIntent;

    private Uri soundURI = Uri
            .parse("android.resource://com.example.course.notificationinstatusbar/"+
    R.raw.alarm_rooster);
    private long[] mVibratePattern = {0, 200, 200, 300};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationIntent = new Intent(getApplicationContext(),
                SubActivity.class);
        mContentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        final Button button = (Button) findViewById(R.id.toast_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification.Builder notificationBuilder = new Notification.Builder(
                        getApplicationContext())
                        .setTicker(tickerText)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setAutoCancel(true)
                        .setContentTitle(contentTitle)
                        .setContentText(
                                contentText +" ("+ ++mNotificationCount+")")
                        .setContentIntent(mContentIntent).setSound(soundURI)
                        .setVibrate(mVibratePattern);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(MY_NOTIFICATION_ID,
                        notificationBuilder.build());
            }
        });


    }
}
