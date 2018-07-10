package task.dst.com.tasks.app.task_details.model;

import com.google.gson.annotations.SerializedName;

public class TaskDetailsResponse{

	@SerializedName("timeStamp")
	private long timeStamp;

	@SerializedName("taskName")
	private String taskName;

	@SerializedName("id")
	private String id;

	@SerializedName("content")
	private String content;

	@SerializedName("open")
	private boolean open;

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

	public void setOpen(boolean open){
		this.open = open;
	}

	public boolean isOpen(){
		return open;
	}

	@Override
 	public String toString(){
		return 
			"TaskDetailsResponse{" + 
			"timeStamp = '" + timeStamp + '\'' + 
			",taskName = '" + taskName + '\'' + 
			",id = '" + id + '\'' + 
			",content = '" + content + '\'' + 
			",open = '" + open + '\'' + 
			"}";
		}
}