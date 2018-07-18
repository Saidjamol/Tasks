package task.dst.com.tasks.app.tasks.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AllTasksResponse implements Serializable {

    @SerializedName("timeStamp")
    private long timeStamp;

    @SerializedName("taskName")
    private String taskName;

    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private String content;

    @SerializedName("endTime")
    private long endTime;

    @SerializedName("taskStatus")
    private TaskStatus taskStatus;

    @SerializedName("recipient")
    private String recipient;

    @SerializedName("lastChangedTimeStamp")
    private long lastChangedTimeStamp;

    public AllTasksResponse(String taskName, String content, TaskStatus taskStatus, long endTime) {
        this.taskName = taskName;
        this.content = content;
        this.taskStatus = taskStatus;
        this.endTime = endTime;
    }

    public long getLastChangedTimeStamp() {
        return lastChangedTimeStamp;
    }

    public void setLastChangedTimeStamp(long lastChangedTimeStamp) {
        this.lastChangedTimeStamp = lastChangedTimeStamp;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }


    @Override
    public String toString() {
        return "AllTasksResponse{" +
                "timeStamp=" + timeStamp +
                ", taskName='" + taskName + '\'' +
                ", id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", endTime=" + endTime +
                ", taskStatus=" + taskStatus +
                '}';
    }
}