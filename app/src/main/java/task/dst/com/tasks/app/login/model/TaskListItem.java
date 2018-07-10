package task.dst.com.tasks.app.login.model;

import com.google.gson.annotations.SerializedName;

public class TaskListItem{

	@SerializedName("timeStamp")
	private long timeStamp;

	@SerializedName("taskName")
	private String taskName;

	@SerializedName("id")
	private String id;

	@SerializedName("content")
	private String content;

	public void setTimeStamp(long timeStamp){
		this.timeStamp = timeStamp;
	}

	public long getTimeStamp(){
		return timeStamp;
	}

	public void setTaskName(String taskName){
		this.taskName = taskName;
	}

	public String getTaskName(){
		return taskName;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	@Override
 	public String toString(){
		return 
			"TaskListItem{" + 
			"timeStamp = '" + timeStamp + '\'' + 
			",taskName = '" + taskName + '\'' + 
			",id = '" + id + '\'' + 
			",content = '" + content + '\'' + 
			"}";
		}
}