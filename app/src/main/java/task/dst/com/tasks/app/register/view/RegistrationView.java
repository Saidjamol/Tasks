package task.dst.com.tasks.app.register.view;

import task.dst.com.tasks.app.register.model.Register;
import task.dst.com.tasks.app.register.model.RegisterResponse;
import task.dst.com.tasks.core.BaseView;

public interface RegistrationView extends BaseView {
    void onSuccess(RegisterResponse response);

    Register registerRequires();
}
