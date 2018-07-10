package task.dst.com.tasks.app.register.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

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
                "RegisterResponse{" +
                        "message = '" + message + '\'' +
                        "}";
    }
}