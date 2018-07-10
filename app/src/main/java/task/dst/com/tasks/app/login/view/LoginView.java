package task.dst.com.tasks.app.login.view;

import task.dst.com.tasks.app.login.model.Login;
import task.dst.com.tasks.app.login.model.LoginResponse;
import task.dst.com.tasks.core.BaseView;

public interface LoginView extends BaseView {

    void onSuccess(LoginResponse response);

    Login loginRequires();
}
