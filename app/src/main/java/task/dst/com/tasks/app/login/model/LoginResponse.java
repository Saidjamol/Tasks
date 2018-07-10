package task.dst.com.tasks.app.login.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("password")
	private String password;

	@SerializedName("name")
	private String name;

	@SerializedName("taskList")
	private List<TaskListItem> taskList;

	@SerializedName("id")
	private String id;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setTaskList(List<TaskListItem> taskList){
		this.taskList = taskList;
	}

	public List<TaskListItem> getTaskList(){
		return taskList;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponse{" + 
			"password = '" + password + '\'' + 
			",name = '" + name + '\'' + 
			",taskList = '" + taskList + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}