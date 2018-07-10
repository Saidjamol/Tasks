package task.dst.com.tasks.app.task_new.view;

import task.dst.com.tasks.app.task_new.model.UserLists;
import task.dst.com.tasks.core.BaseView;

public interface NewTaskView extends BaseView {

    void onGetUsers(UserLists userListResponse);
}
