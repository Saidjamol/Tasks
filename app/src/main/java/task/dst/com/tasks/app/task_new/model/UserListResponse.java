package task.dst.com.tasks.app.task_new.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserListResponse{

	@SerializedName("userList")
	private List<UserListItem> userList;

	public void setUserList(List<UserListItem> userList){
		this.userList = userList;
	}

	public List<UserListItem> getUserList(){
		return userList;
	}

	@Override
 	public String toString(){
		return 
			"UserListResponse{" + 
			"userList = '" + userList + '\'' + 
			"}";
		}
}