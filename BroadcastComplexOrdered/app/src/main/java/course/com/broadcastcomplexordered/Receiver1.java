package course.com.broadcastcomplexordered;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by akshay on 31/7/16.
 */
public class Receiver1 extends BroadcastReceiver {

    private final String TAG="Receiver1";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG,"INTENT RECEIVED");

        if(isOrderedBroadcast()){
            Log.i(TAG,"Calling abortBroadcast()");
            abortBroadcast();
        }

        Vibrator v = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);

        Toast.makeText(context,"INTENT RECEIVED by Receiver1",Toast.LENGTH_LONG).show();

    }
}
