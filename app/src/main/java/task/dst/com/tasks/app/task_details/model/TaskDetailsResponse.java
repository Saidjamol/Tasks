package task.dst.com.tasks.app.task_details.model;

import com.google.gson.annotations.SerializedName;

import task.dst.com.tasks.app.tasks.model.TaskStatus;

public class TaskDetailsResponse{

	@SerializedName("timeStamp")
	private long timeStamp;

	@SerializedName("taskName")
	private String taskName;

	@SerializedName("id")
	private String id;

	@SerializedName("content")
	private String content;

	@SerializedName("taskStatus")
	private TaskStatus taskStatus;

	@SerializedName("endTime")
	private long endTime;

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
}