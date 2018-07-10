package task.dst.com.tasks.core;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import task.dst.com.tasks.services.TaskService;

public abstract class BasePresenterImpl<T extends BaseView> implements BasePresenter {

    protected T view;
    protected TaskService taskService = new TaskService();

    public BasePresenterImpl(T t) {
        this.view = t;
    }

    @Override
    public void attachView(Object view) {
        this.view = (T) view;
        this.view.showToast("Attached " + view.getClass().getName());
    }

    @Override
    public void detachView() {
        view.showToast("Detached " + view.getClass().getName());
        view = null;
    }

    public static String parseTime(final long time) {
        final Calendar today = Calendar.getInstance();
        final Calendar otherDay = Calendar.getInstance();
        otherDay.setTimeInMillis(time);
        String result = null;
        if (today.get(Calendar.DATE) == otherDay.get(Calendar.DATE) && today.get(Calendar.MONTH) == otherDay.get(Calendar.MONTH) && today.get(Calendar.YEAR) == otherDay.get(Calendar.YEAR)) {
            result = String.format("%s at %s", "today", new SimpleDateFormat("hh:mm a", Locale.US).format(otherDay.getTimeInMillis()));
        } else if (today.get(Calendar.DATE) - 1 == otherDay.get(Calendar.DATE) && today.get(Calendar.MONTH) == otherDay.get(Calendar.MONTH) && today.get(Calendar.YEAR) == otherDay.get(Calendar.YEAR)) {
            result = String.format("%s at %s", "yesterday", new SimpleDateFormat("hh:mm a", Locale.US).format(otherDay.getTimeInMillis()));
        } else {
            result = new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(otherDay.getTimeInMillis());
        }
        return result;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    @Override
    public void startTaskSevice(Context context) {
//        taskService = new TaskService();
        Intent intent = new Intent(context, taskService.getClass());
        if (!isMyServiceRunning(taskService.getClass(), context)) {
            context.startService(intent);
        }
    }
}
