package task.dst.com.tasks.app.register.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import task.dst.com.tasks.BaseFragment;
import task.dst.com.tasks.R;
import task.dst.com.tasks.app.login.view.LoginFragment;
import task.dst.com.tasks.app.register.model.Register;
import task.dst.com.tasks.app.register.model.RegisterResponse;
import task.dst.com.tasks.app.register.presenter.RegistrationPresenterImpl;
import task.dst.com.tasks.core.UtilInterface;
import task.dst.com.tasks.databinding.FragmentRegistrationBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends BaseFragment<RegistrationPresenterImpl> implements RegistrationView {

    private FragmentRegistrationBinding binding;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        presenterView = new RegistrationPresenterImpl(this);

        binding.register.setOnClickListener(v -> {
            presenterView.register();
        });
    }

    @Override
    public void onSuccess(RegisterResponse response) {
        ((UtilInterface) getActivity()).switchFragment(new LoginFragment());
    }

    @Override
    public Register registerRequires() {
        Register register = new Register();
        register.setUsername(binding.username.getText().toString());
        register.setPassword(binding.username.getText().toString());
        register.setEmail(binding.email.getText().toString());
        return register;
    }
}
