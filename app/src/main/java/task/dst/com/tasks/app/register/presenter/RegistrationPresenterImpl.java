package task.dst.com.tasks.app.register.presenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import task.dst.com.tasks.apiUtils.ApiClient;
import task.dst.com.tasks.apiUtils.ApiInterface;
import task.dst.com.tasks.app.register.model.Register;
import task.dst.com.tasks.app.register.model.RegisterResponse;
import task.dst.com.tasks.app.register.view.RegistrationView;
import task.dst.com.tasks.core.BasePresenterImpl;

public class RegistrationPresenterImpl extends BasePresenterImpl<RegistrationView> implements RegistrationPresenter {

    public RegistrationPresenterImpl(RegistrationView registrationView) {
        super(registrationView);
    }

    @Override
    public void register() {
        Register register = view.registerRequires();

        ApiClient.getClient()
                .create(ApiInterface.class)
                .register(register.getUsername(), register.getPassword(), register.getEmail())
                .enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 201) {
                                view.showToast(response.body().getMessage());
                                view.onSuccess(response.body());
                            } else {
                                view.showToast(response.body().getMessage());
                            }
                        } else {
                            view.showToast(response.body().getMessage());
                            view.onError(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        view.showToast("Fail");
                    }
                });
    }
}
