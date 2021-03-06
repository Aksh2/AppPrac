package com.example.course.broadcastreceiverdynamic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by akshay on 29/7/16.
 */
public class Receiver extends BroadcastReceiver {
    private final String TAG="Receiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG,"INTENT RECEIVED");

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        v.vibrate(500);

        Toast.makeText(context,"INTENT RECEIVED by Receiver",Toast.LENGTH_LONG).show();

    }
}
