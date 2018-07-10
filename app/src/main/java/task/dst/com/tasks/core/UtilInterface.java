package task.dst.com.tasks.core;

import android.support.v4.app.Fragment;

public interface UtilInterface {

    default void switchFragment(final Fragment fragment) {

    }

    default void switchFragmentAddToBackStack(final Fragment fragment) {

    }

    default void showTopMenu(final boolean show) {

    }
}
