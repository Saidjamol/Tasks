package task.dst.com.tasks.app.task_details.presenter;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import task.dst.com.tasks.apiUtils.ApiClient;
import task.dst.com.tasks.apiUtils.ApiInterface;
import task.dst.com.tasks.app.task_details.model.TaskDetailsResponse;
import task.dst.com.tasks.app.task_details.model.TaskDoneResponse;
import task.dst.com.tasks.app.task_details.view.TaskDetailsView;
import task.dst.com.tasks.core.BasePresenterImpl;

public class TaskDetailsPresenterImpl extends BasePresenterImpl<TaskDetailsView> implements TaskDetailsPresenter {
    public TaskDetailsPresenterImpl(TaskDetailsView taskDetailsView) {
        super(taskDetailsView);
    }

    @Override
    public void getTaskDetails() {
        ApiClient.getClient()
                .create(ApiInterface.class)
                .getTaskDetails(view.getTaskId())
                .enqueue(new Callback<TaskDetailsResponse>() {
                    @Override
                    public void onResponse(Call<TaskDetailsResponse> call, Response<TaskDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            view.onSuccess(response.body());
                        } else {
                            view.showToast("Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<TaskDetailsResponse> call, Throwable t) {
                        view.showToast("Fail");
                    }
                });
    }

    @Override
    public void closeTask() {
        ApiClient.getClient()
                .create(ApiInterface.class)
                .taskDone(Paper.book().read("userId"), view.getTaskId())
                .enqueue(new Callback<TaskDoneResponse>() {
                    @Override
                    public void onResponse(Call<TaskDoneResponse> call, Response<TaskDoneResponse> response) {
                        if (response.isSuccessful()) {
                            view.showToast("Task successfully closed");
                            view.onCloseTask();
                        } else {
                            view.showToast("Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<TaskDoneResponse> call, Throwable t) {
                        view.showToast("Fail");
                    }
                });
    }
}
