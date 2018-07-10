package task.dst.com.tasks.core;

import android.content.Context;

public interface BasePresenter {
    void attachView(Object view);

    void detachView();

    void startTaskSevice(Context context);
}
