package task.dst.com.tasks.app.tasks.model.scheduleTaskModel;

import com.google.gson.annotations.SerializedName;

public class TaskScheduleResponse {

    @SerializedName("message")
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return
                "TaskScheduleResponse{" +
                        "message = '" + message + '\'' +
                        "}";
    }
}