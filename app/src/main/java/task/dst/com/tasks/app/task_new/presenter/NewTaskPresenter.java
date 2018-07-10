package task.dst.com.tasks.app.task_new.presenter;

import task.dst.com.tasks.app.tasks.model.AllTasksResponse;
import task.dst.com.tasks.core.BasePresenter;

public interface NewTaskPresenter extends BasePresenter {
    void getUsers();

    void onUserSelected(final String userId);

    boolean createNewTask(AllTasksResponse newTask);
}
