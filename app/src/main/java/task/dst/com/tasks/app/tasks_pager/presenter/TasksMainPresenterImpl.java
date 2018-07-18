package task.dst.com.tasks.app.tasks_pager.presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import task.dst.com.tasks.app.tasks.view.TasksFragment;
import task.dst.com.tasks.app.tasks_pager.view.TasksMainView;
import task.dst.com.tasks.core.BasePresenterImpl;

public class TasksMainPresenterImpl extends BasePresenterImpl<TasksMainView> implements TasksMainPresenter {
    public TasksMainPresenterImpl(TasksMainView tasksMainView) {
        super(tasksMainView);
    }

    @Override
    public List<Fragment> getFragments() {
        final List<Fragment> list = new ArrayList<>(2);

        for (int i = 0; i < 2; i++) {
            final Bundle bundle = new Bundle();
            bundle.putInt("TASK_TAB", i);
            final TasksFragment tasksFragment = new TasksFragment();
            tasksFragment.setArguments(bundle);
            list.add(tasksFragment);
        }

        return list;
    }

    @Override
    public List<String> getTabTitles() {
        final List<String> titles = new ArrayList<>(2);
        titles.add("Received Tasks");
        titles.add("Sent Tasks");
        return titles;
    }
}
