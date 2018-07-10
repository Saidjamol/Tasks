package task.dst.com.tasks.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;

import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import task.dst.com.tasks.R;
import task.dst.com.tasks.app.tasks.model.AllTasksResponse;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;

import static task.dst.com.tasks.apiUtils.ApiClient.BASE_URL;

public class TaskService extends Service {

    private static StompClient stompClient;

    public TaskService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Paper.init(this);
        connectSockets();
        return START_STICKY;
    }

    private void connectSockets() {
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, BASE_URL + "ws");
        stompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
//                            mStompClient.send("/app/status", new Gson().toJson(new UserStatus(id, true)));
                            Log.d("TOPICSUB", "Stomp connection opened " + lifecycleEvent.getMessage());
                            break;
                        case ERROR:
//                            mStompClient.send("/app/status", new Gson().toJson(new UserStatus(id, false)));
                            Log.e("TOPICSUB", "Stomp connection error", lifecycleEvent.getException());
                            break;
                        case CLOSED:
//                            mStompClient.send("/app/status", new Gson().toJson(new UserStatus(id, false)));
                            Log.d("TOPICSUB", "Stomp connection closed");
                            break;
                    }
                });

        stompClient.connect();

        stompClient.topic("/topic/" + Paper.book().read("userId"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    Gson gson = new Gson();
                    AllTasksResponse response = gson.fromJson(topicMessage.getPayload(), AllTasksResponse.class);

                    Intent intent = new Intent("MESSAGE");
                    intent.putExtra("TASK", response);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//                    showNotification(response.getTaskName());

                    Log.d("TOPICSUB", "Received " + topicMessage.getPayload());
                });


    }

    private void showNotification(String content) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);

        Intent notificationIntent = getPackageManager().getLaunchIntentForPackage("com.example.msi.taskmaker").putExtra("taskmi", content).setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        //set
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.drawable.ic_logout_selector);
        builder.setContentText(content);
        builder.setContentTitle("New Task");
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);

        builder.setDefaults(Notification.DEFAULT_ALL);

        Notification notification = builder.build();
        nm.notify(content, 1, notification);
    }

    public void sendTask(AllTasksResponse newTask, String id) {
        if (newTask != null && id.equals("")) {
            Log.d("TOPICSUB", newTask.toString());
            Log.d("TOPICSUB", id);
            stompClient.send("/app/" + id, new Gson().toJson(newTask)).subscribe();
        }
    }

    public void disconnectSocket() {
        stompClient.disconnect();
    }

    public void subscribeTask(String taskId) {
        stompClient.topic("/topic/" + taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    Log.d("TOPIC", String.valueOf(topicMessage));
                });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
