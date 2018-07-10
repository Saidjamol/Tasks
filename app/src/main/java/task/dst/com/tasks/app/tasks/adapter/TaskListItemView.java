package task.dst.com.tasks.app.tasks.adapter;

import task.dst.com.tasks.app.tasks.model.TaskStatus;

public interface TaskListItemView {

    void setTaskName(String taskName);
    void setTaskMsg(String taskMsg);
    void setTaskTime(String taskTime);
    void setTaskStatus(final TaskStatus status);
}
