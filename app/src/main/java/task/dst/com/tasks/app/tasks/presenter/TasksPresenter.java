package task.dst.com.tasks.app.tasks.presenter;

import android.content.Intent;

import task.dst.com.tasks.app.tasks.adapter.TaskListItemView;
import task.dst.com.tasks.app.tasks.model.AllTasksResponse;
import task.dst.com.tasks.core.BasePresenter;

public interface TasksPresenter extends BasePresenter {
    void onBindTaskAtPosition(int position, TaskListItemView itemView);

    int getTaskListCount();

    void getTasks();

    void onItemClickedAtPosition(int adapterPosition);

//    boolean createNewTask(AllTasksResponse newTask);
//
//    void getUsers();
//
//    void onUserSelected(final String userId);

//    void startTaskService(Context context);

    void updateTaskReceiveList(AllTasksResponse response);

    void updateTaskList(Intent intent, int task_tab);

    void getSentTasks();

    void setCurrentTab(final int currentTab);

    int getCurrentTab();

}
