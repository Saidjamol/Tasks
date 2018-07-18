package task.dst.com.tasks.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Date;

import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import task.dst.com.tasks.MainActivity;
import task.dst.com.tasks.R;
import task.dst.com.tasks.app.tasks.model.AllTasksResponse;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;

import static task.dst.com.tasks.apiUtils.ApiClient.BASE_URL;

public class TaskService extends Service {

    private static StompClient stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, BASE_URL + "ws");

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
                            stompClient.connect();
                            break;
                        case CLOSED:
//                            mStompClient.send("/app/status", new Gson().toJson(new UserStatus(id, false)));
                            Log.d("TOPICSUB", "Stomp connection closed");
                            stompClient.connect();
                            break;
                    }
                });

        if (!stompClient.isConnected()) {
            stompClient.connect();
        }

        subscribeToReceiveTask();
        subscribeToCreatedTask();
    }

    private void showNotification(AllTasksResponse content) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("NOTIFICATION", content.getId());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        //set
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.drawable.ic_logout_selector);
        builder.setContentText(content.getContent());
        builder.setContentTitle(content.getTaskName());
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setDefaults(Notification.DEFAULT_ALL);

        Notification notification = builder.build();
        nm.notify((int) System.currentTimeMillis(), notification);
    }

    public void sendTask(AllTasksResponse newTask, String receiverId) {
        Log.d("TOPICSUB", newTask.toString());
        stompClient.send("/app/" + Paper.book().read("userId") + "/" + receiverId, new Gson().toJson(newTask)).subscribe();
    }

    public void subscribeToReceiveTask() {
        stompClient.topic("/topic/" + Paper.book().read("userId"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    AllTasksResponse response = new Gson().fromJson(topicMessage.getPayload(), AllTasksResponse.class);

                    showNotification(response);
                    Intent intent = new Intent("MESSAGE");
                    intent.putExtra("TASK", response);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                    Log.d("TOPICSUB", "Received " + topicMessage.getPayload());
                });
    }

    public void subscribeToCreatedTask() {
        stompClient.topic("/topic/my_task/" + Paper.book().read("userId"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    Log.d("TOPIC_TASK", "Received" + String.valueOf(topicMessage.getPayload()));
                    AllTasksResponse task = new Gson().fromJson(topicMessage.getPayload(), AllTasksResponse.class);
                    subscribeToCreatedTaskUpdate(task.getId());
                });
    }

    public void subscribeTaskUpdate(String taskId) {
        stompClient.topic("/topic/" + taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    Log.d("TOPIC_TASK", "Received: " + String.valueOf(topicMessage.getPayload()));

                    AllTasksResponse response = new Gson().fromJson(topicMessage.getPayload(), AllTasksResponse.class);

                    Intent intent = new Intent("TASK_UPDATE");
                    intent.putExtra("TASK", response);

                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                });
    }

    public void subscribeToCreatedTaskUpdate(String taskId) {
        stompClient.topic("/topic/my_task/" + taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    Log.d("TOPIC_TASK", "Received: " + String.valueOf(topicMessage.getPayload()));
                });
    }

    public void disconnectSocket() {
        Log.d("STOMP", "STOPM closed");
        stompClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disconnectSocket();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
