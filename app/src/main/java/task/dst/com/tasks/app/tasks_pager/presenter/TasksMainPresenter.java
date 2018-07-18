package task.dst.com.tasks.app.tasks_pager.presenter;

import android.support.v4.app.Fragment;

import java.util.List;

import task.dst.com.tasks.core.BasePresenter;

public interface TasksMainPresenter extends BasePresenter{

    List<Fragment> getFragments();

    List<String> getTabTitles();
}
