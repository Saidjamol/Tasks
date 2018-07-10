package task.dst.com.tasks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import io.paperdb.Paper;
import task.dst.com.tasks.app.login.view.LoginFragment;
import task.dst.com.tasks.app.tasks.view.TasksFragment;
import task.dst.com.tasks.core.UtilInterface;

public class MainActivity extends BaseActivity implements UtilInterface {

    private Menu myMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);

        switchFragment(Paper.book().read("isLogged", false) ? new TasksFragment() : new LoginFragment());
    }

    @Override
    public void switchFragment(Fragment fragment) {
        switchFragments(fragment);
    }

    @Override
    public void switchFragmentAddToBackStack(Fragment fragment) {
        switchFragmentsAddToBackStack(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        menu.setGroupVisible(0, false);
        myMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Paper.book().destroy();
                switchFragment(new LoginFragment());
                break;
        }

        return true;
    }

    @Override
    public void showTopMenu(boolean show) {
        if (myMenu != null) {
            myMenu.setGroupVisible(0, show);
        }
    }
}
