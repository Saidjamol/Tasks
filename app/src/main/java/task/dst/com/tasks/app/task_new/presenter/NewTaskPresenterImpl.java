package task.dst.com.tasks.app.task_new.presenter;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import task.dst.com.tasks.apiUtils.ApiClient;
import task.dst.com.tasks.apiUtils.ApiInterface;
import task.dst.com.tasks.app.task_new.model.UserListItem;
import task.dst.com.tasks.app.task_new.model.UserLists;
import task.dst.com.tasks.app.task_new.view.NewTaskView;
import task.dst.com.tasks.app.task_new.model.UserListResponse;
import task.dst.com.tasks.app.tasks.model.AllTasksResponse;
import task.dst.com.tasks.core.BasePresenterImpl;

public class NewTaskPresenterImpl extends BasePresenterImpl<NewTaskView> implements NewTaskPresenter {

    private String userId;

    public NewTaskPresenterImpl(NewTaskView newTaskView) {
        super(newTaskView);
    }

    @Override
    public void getUsers() {
        ApiClient.getClient()
                .create(ApiInterface.class)
                .getUsers(Paper.book().read("userId"))
                .enqueue(new Callback<UserListResponse>() {
                    @Override
                    public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                        if (response.isSuccessful()) {
                            view.onGetUsers(setUpUserList(response.body().getUserList()));
                        } else {
                            view.showToast("Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserListResponse> call, Throwable t) {
                        view.showToast("Fail");
                    }
                });
    }

    @Override
    public void onUserSelected(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean createNewTask(AllTasksResponse newTask) {
        Log.d("NEWTASK", new Gson().toJson(newTask));
        taskService.sendTask(newTask, userId);
        return true;
    }

    private UserLists setUpUserList(List<UserListItem> userList) {
        List<String> users = new ArrayList<>();
        HashMap<String, String> usersHashmap = new HashMap<>();

        for (UserListItem item : userList) {
            users.add(item.getName());
            usersHashmap.put(item.getName(), item.getId());
        }

        return new UserLists(users, usersHashmap);
    }
}
