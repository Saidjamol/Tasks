package task.dst.com.tasks;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import task.dst.com.tasks.app.login.view.LoginFragment;
import task.dst.com.tasks.app.register.view.RegistrationFragment;
import task.dst.com.tasks.core.BasePresenterImpl;
import task.dst.com.tasks.core.BaseView;
import task.dst.com.tasks.core.UtilInterface;

public abstract class BaseFragment<T extends BasePresenterImpl> extends Fragment implements BaseView {

    protected T presenterView;

    @Override
    public void onError(Object object) {

    }

//    public void registerPresenterView(Object object) {
//        presenterView = (T) object;
//    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoader(boolean show) {

    }

    @Override
    public void onStart() {
        super.onStart();
//        presenterView.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
//        presenterView.detachView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenterView.detachView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context != null) {
//            if (this instanceof LoginFragment || this instanceof RegistrationFragment) {
//                ((UtilInterface) getActivity()).showTopMenu(false);
//            } else {
////                new Handler().postDelayed(() -> {
//                    ((UtilInterface) getActivity()).showTopMenu(true);
////                }, 300);
//
//            }
//        }
    }


}
