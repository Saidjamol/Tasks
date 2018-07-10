package task.dst.com.tasks.core;

public interface BaseView {

    void onError(Object object);

    void showToast(String text);

    void showLoader(boolean show);
}
