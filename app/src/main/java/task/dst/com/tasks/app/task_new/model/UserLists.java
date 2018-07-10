package task.dst.com.tasks.app.task_new.model;

import java.util.HashMap;
import java.util.List;

public class UserLists {

    private List<String> users;
    private HashMap<String, String> userList;

    public UserLists(List<String> users, HashMap<String, String> userList) {
        this.users = users;
        this.userList = userList;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public HashMap<String, String> getUserList() {
        return userList;
    }

    public void setUserList(HashMap<String, String> userList) {
        this.userList = userList;
    }
}
