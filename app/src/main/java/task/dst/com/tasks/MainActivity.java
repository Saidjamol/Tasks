package task.dst.com.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import io.paperdb.Paper;
import task.dst.com.tasks.app.login.view.LoginFragment;
import task.dst.com.tasks.app.task_details.view.TaskDetailsFragment;
import task.dst.com.tasks.app.tasks.view.TasksFragment;
import task.dst.com.tasks.app.tasks_pager.view.TasksMainFragment;
import task.dst.com.tasks.core.UtilInterface;
import task.dst.com.tasks.services.TaskService;

public class MainActivity extends BaseActivity implements UtilInterface {

    private Menu myMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);

        switchFragment(Paper.book().read("isLogged", false) ? new TasksMainFragment() : new LoginFragment());

        if (getIntent().hasExtra("NOTIFICATION")) {
            final Bundle bundle = new Bundle();
            bundle.putString("taskId", getIntent().getStringExtra("NOTIFICATION"));
            TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();
            taskDetailsFragment.setArguments(bundle);
            switchFragmentAddToBackStack(taskDetailsFragment);
            getIntent().getExtras().clear();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra("NOTIFICATION")) {
            final Bundle bundle = new Bundle();
            bundle.putString("taskId", intent.getStringExtra("NOTIFICATION"));
            TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();
            taskDetailsFragment.setArguments(bundle);
            switchFragmentAddToBackStack(taskDetailsFragment);
        }
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
//        menu.setGroupVisible(0, false);
        myMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                stopService(new Intent(this, TaskService.class));
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
