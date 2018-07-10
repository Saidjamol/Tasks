package task.dst.com.tasks.app.tasks.view;

import java.util.List;

import task.dst.com.tasks.app.tasks.model.AllTasksResponse;
import task.dst.com.tasks.app.task_new.model.UserLists;
import task.dst.com.tasks.core.BaseView;

public interface TasksView extends BaseView {

    void onSuccess(List<AllTasksResponse> response);

    void openTaskDetails(final String taskId);

    void setRefreshView(final boolean enable);

//    void onGetUsers(UserLists userListResponse);

    void refreshTaskAdapter();

}
