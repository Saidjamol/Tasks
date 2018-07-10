package task.dst.com.tasks.app.tasks.presenter;

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
import task.dst.com.tasks.app.tasks.view.TasksView;
import task.dst.com.tasks.core.BasePresenterImpl;

public class TasksPresenterImpl extends BasePresenterImpl<TasksView> implements TasksPresenter {

    private final List<AllTasksResponse> allTasksList = new ArrayList<>();
//    private String userId;
//    private TaskService taskService;

    public TasksPresenterImpl(TasksView tasksView) {
        super(tasksView);
    }

//    @Override
//    public void startTaskService(Context context) {
//        taskService = new TaskService();
//        Intent intent = new Intent(context, taskService.getClass());
//        if (!isMyServiceRunning(taskService.getClass(), context)) {
//            context.startService(intent);
//        }
//    }

    @Override
    public void updateTaskList(AllTasksResponse response) {
        allTasksList.add(0, response);
        view.refreshTaskAdapter();
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
        itemView.setTaskTime(parseTime(allTasksList.get(position).getTimeStamp()));
        itemView.setTaskStatus(allTasksList.get(position).getTaskStatus());
    }

    @Override
    public int getTaskListCount() {
        return allTasksList.size();
    }
}
