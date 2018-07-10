package task.dst.com.tasks.app.task_details.view;

import task.dst.com.tasks.app.task_details.model.TaskDetailsResponse;
import task.dst.com.tasks.core.BaseView;

public interface TaskDetailsView extends BaseView {
    void onSuccess(TaskDetailsResponse response);

    String getTaskId();
}
