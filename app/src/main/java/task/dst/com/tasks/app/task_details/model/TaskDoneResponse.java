package task.dst.com.tasks.app.task_details.model;

import com.google.gson.annotations.SerializedName;

public class TaskDoneResponse{

	@SerializedName("message")
	private String message;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"TaskDoneResponse{" + 
			"message = '" + message + '\'' + 
			"}";
		}
}