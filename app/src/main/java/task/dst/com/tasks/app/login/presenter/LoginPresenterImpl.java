package task.dst.com.tasks.app.login.presenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import task.dst.com.tasks.apiUtils.ApiClient;
import task.dst.com.tasks.apiUtils.ApiInterface;
import task.dst.com.tasks.app.login.model.Login;
import task.dst.com.tasks.app.login.model.LoginResponse;
import task.dst.com.tasks.app.login.view.LoginView;
import task.dst.com.tasks.core.BasePresenterImpl;

public class LoginPresenterImpl extends BasePresenterImpl<LoginView> implements LoginPresenter {

    public LoginPresenterImpl(LoginView loginView) {
        super(loginView);
    }

    //    private LoginView presenterView;
//    public LoginPresenterImpl() {
//
//    }
//
//    public LoginPresenterImpl(LoginView loginView) {
//        super(loginView);
////        this.presenterView = loginView;
//    }

//    @Override
//    public void attachView(Object presenterView) {
//        this.presenterView = (LoginView) presenterView;
//    }
//
//    @Override
//    public void detachView() {
//        presenterView = null;
//    }

    @Override
    public void login() {
        Login login = view.loginRequires();

        ApiClient.getClient()
                .create(ApiInterface.class)
                .login(login.getUsername(), login.getPassword())
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            view.showToast("Success");
                            view.onSuccess(response.body());
                        } else {
                            view.onError(response.body());
                            view.showToast("Not Success");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        view.showToast("Fail");
                    }
                });
    }
}
