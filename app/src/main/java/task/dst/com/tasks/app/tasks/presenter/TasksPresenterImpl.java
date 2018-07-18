package task.dst.com.tasks.app.tasks.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import task.dst.com.tasks.apiUtils.ApiClient;
import task.dst.com.tasks.apiUtils.ApiInterface;
import task.dst.com.tasks.app.tasks.adapter.TaskListItemView;
import task.dst.com.tasks.app.tasks.model.AllTasksResponse;
import task.dst.com.tasks.app.tasks.model.scheduleTaskModel.TaskScheduleResponse;
import task.dst.com.tasks.app.tasks.view.TasksView;
import task.dst.com.tasks.core.BasePresenterImpl;

public class TasksPresenterImpl extends BasePresenterImpl<TasksView> implements TasksPresenter {

    private final List<AllTasksResponse> allTasksList = new ArrayList<>();

    public TasksPresenterImpl(TasksView tasksView) {
        super(tasksView);
    }

    @Override
    public void updateTaskList(AllTasksResponse response) {
            Log.d("RECEIVED_TASK", response.toString());
            allTasksList.add(0, response);
            if (response.getEndTime() != 0) {
                sendTaskSchedule(Paper.book().read("userId"), response.getId());
            }
            view.refreshTaskAdapter();
    }

    private void sendTaskSchedule(String user_id, String task_id) {
        ApiClient.getClient()
                .create(ApiInterface.class)
                .sendTaskSchedule(user_id, task_id)
                .enqueue(new Callback<TaskScheduleResponse>() {
                    @Override
                    public void onResponse(Call<TaskScheduleResponse> call, Response<TaskScheduleResponse> response) {
                        if (response.isSuccessful()) {
                            view.showToast("SCHEDULE " + response.body().getMessage());
                            taskService.subscribeTaskUpdate(task_id);
                        } else {
                            view.showToast("ERROR SCHEDULE");
                        }

                    }

                    @Override
                    public void onFailure(Call<TaskScheduleResponse> call, Throwable t) {
                        view.showToast("FAIL");
                    }
                });
    }

    @Override
    public void getTasks() {
        ApiClient.getClient()
                .create(ApiInterface.class)
                .fetchTasks(Paper.book().read("userId"))
                .enqueue(new Callback<List<AllTasksResponse>>() {
                    @Override
                    public void onResponse(Call<List<AllTasksResponse>> call, Response<List<AllTasksResponse>> response) {
                        if (response.isSuccessful()) {
                            view.onSuccess(response.body());
                            allTasksList.clear();
                            if (response.body() != null) {
                                allTasksList.addAll(Objects.requireNonNull(response.body()));
                            }
                        } else {
                            view.showToast("Error");
                            view.onError(response.body());
                        }
                        view.setRefreshView(false);
                    }

                    @Override
                    public void onFailure(Call<List<AllTasksResponse>> call, Throwable t) {
                        view.showToast("Fail");
                        t.printStackTrace();
                        view.setRefreshView(false);
                    }
                });
    }

    @Override
    public void getSentTasks() {
        ApiClient.getClient()
                .create(ApiInterface.class)
                .fetchSentTasks(Paper.book().read("userId"))
                .enqueue(new Callback<List<AllTasksResponse>>() {
                    @Override
                    public void onResponse(Call<List<AllTasksResponse>> call, Response<List<AllTasksResponse>> response) {
                        if (response.isSuccessful()) {
                            view.onSuccess(response.body());
                            allTasksList.clear();
                            if (response.body() != null) {
                                allTasksList.addAll(Objects.requireNonNull(response.body()));
                            }
                        } else {
                            view.showToast("Error");
                            view.onError(response.body());
                        }
                        view.setRefreshView(false);
                    }

                    @Override
                    public void onFailure(Call<List<AllTasksResponse>> call, Throwable t) {
                        view.showToast("Fail");
                        t.printStackTrace();
                        view.setRefreshView(false);
                    }
                });
    }

//    @Override
//    public boolean createNewTask(AllTasksResponse newTask) {
//        taskService.sendTask(newTask, userId);
//        return true;
//    }

//    @Override
//    public void getUsers() {
//        ApiClient.getClient()
//                .create(ApiInterface.class)
//                .getUsers(Paper.book().read("userId"))
//                .enqueue(new Callback<UserListResponse>() {
//                    @Override
//                    public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
//                        if (response.isSuccessful()) {
//                            view.onGetUsers(setUpUserList(response.body().getUserList()));
//                        } else {
//                            view.showToast("Error");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<UserListResponse> call, Throwable t) {
//                        view.showToast("Fail");
//                    }
//                });
//    }

//    @Override
//    public void onUserSelected(String userId) {
//        this.userId = userId;
//        Log.d("USERID", userId);
//    }

//    private UserLists setUpUserList(List<UserListItem> userList) {
//        List<String> users = new ArrayList<>();
//        HashMap<String, String> usersHashmap = new HashMap<>();
//
//        for (UserListItem item : userList) {
//            users.add(item.getName());
//            usersHashmap.put(item.getName(), item.getId());
//        }
//
//        return new UserLists(users, usersHashmap);
//    }

    @Override
    public void onItemClickedAtPosition(int adapterPosition) {
        view.openTaskDetails(allTasksList.get(adapterPosition).getId());
    }

    @Override
    public void onBindTaskAtPosition(int position, TaskListItemView itemView) {
        itemView.setTaskName(allTasksList.get(position).getTaskName());
        itemView.setTaskMsg(allTasksList.get(position).getContent());
        itemView.setTaskTime(parseTime(allTasksList.get(position).getLastChangedTimeStamp()));
        itemView.setTaskStatus(allTasksList.get(position).getTaskStatus());
    }

    @Override
    public int getTaskListCount() {
        return allTasksList.size();
    }
}
