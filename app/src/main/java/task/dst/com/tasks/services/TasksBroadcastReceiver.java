package task.dst.com.tasks.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TasksBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BROADCASTRECEIVER", "Service Stops! Oops!!!!");
        context.startService(new Intent(context, TaskService.class));
    }
}
