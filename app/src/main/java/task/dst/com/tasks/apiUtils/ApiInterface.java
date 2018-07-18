package task.dst.com.tasks.apiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import task.dst.com.tasks.app.login.model.LoginResponse;
import task.dst.com.tasks.app.register.model.RegisterResponse;
import task.dst.com.tasks.app.task_details.model.TaskDetailsResponse;
import task.dst.com.tasks.app.task_details.model.TaskDoneResponse;
import task.dst.com.tasks.app.tasks.model.AllTasksResponse;
import task.dst.com.tasks.app.task_new.model.UserListResponse;
import task.dst.com.tasks.app.tasks.model.scheduleTaskModel.TaskScheduleResponse;

public interface ApiInterface {
    @GET("api/login")
    Call<LoginResponse> login(@Query("name") final String username, @Query("password") final String password);

    @POST("api/register_user")
    Call<RegisterResponse> register(@Query("name") final String username, @Query("password") final String password);

    @GET("api/get_tasks/{user_id}")
    Call<List<AllTasksResponse>> fetchTasks(@Path("user_id") final String userId);

    @GET("api/get_single_task/{task_id}")
    Call<TaskDetailsResponse> getTaskDetails(@Path("task_id") final String task_id);

    @PUT("api/close_task/{user_id}/{task_id}")
    Call<TaskDoneResponse> taskDone(@Path("user_id") final String user_id, @Path("task_id") final String task_id);

    @GET("api/get_all_users/{user_id}")
    Call<UserListResponse> getUsers(@Path("user_id") final String user_id);

    @GET("api/schedule_task/{userId}/{taskId}")
    Call<TaskScheduleResponse> sendTaskSchedule(@Path("userId") final String user_id, @Path("taskId") final String task_id);

    @GET("api/sent_task_list/{user_id}")
    Call<List<AllTasksResponse>> fetchSentTasks(@Path("user_id") final String userId);
}
