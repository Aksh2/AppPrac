package com.example.course.broadcastreceiverwithresultcollection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by akshay on 3/8/16.
 */
public class Receiver3 extends BroadcastReceiver {

    private final String TAG = "Receiver3";
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG,"INTENT RECEIVED by Receiver3");

        String tmp = getResultData()==null? "":getResultData();
        setResultData(tmp+":Receiver 3");

    }
}
