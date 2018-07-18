package task.dst.com.tasks.app.login.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import io.paperdb.Paper;
import task.dst.com.tasks.BaseFragment;
import task.dst.com.tasks.R;
import task.dst.com.tasks.app.login.model.Login;
import task.dst.com.tasks.app.login.model.LoginResponse;
import task.dst.com.tasks.app.login.presenter.LoginPresenterImpl;
import task.dst.com.tasks.app.register.view.RegistrationFragment;
import task.dst.com.tasks.app.tasks.view.TasksFragment;
import task.dst.com.tasks.app.tasks_pager.view.TasksMainFragment;
import task.dst.com.tasks.core.UtilInterface;
import task.dst.com.tasks.databinding.FragmentLoginBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment<LoginPresenterImpl> implements LoginView {

    private FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        setHasOptionsMenu(true);
        init();
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private void init() {
//        presenterView = new LoginPresenterImpl(this);
        presenterView = new LoginPresenterImpl(this);

        binding.login.setOnClickListener(v -> {
            presenterView.login();
        });

        binding.register.setOnClickListener(v -> {
            ((UtilInterface) getActivity()).switchFragmentAddToBackStack(new RegistrationFragment());
        });
    }

    @Override
    public void onSuccess(LoginResponse response) {
        Paper.book().write("isLogged", true);
        Paper.book().write("userId", response.getId());
//        ((UtilInterface) getActivity()).switchFragment(new TasksFragment());
        ((UtilInterface) getActivity()).switchFragment(new TasksMainFragment());
    }

    @Override
    public Login loginRequires() {
        Login login = new Login();
        login.setUsername(binding.username.getText().toString());
        login.setPassword(binding.password.getText().toString());
        return login;
    }

}
