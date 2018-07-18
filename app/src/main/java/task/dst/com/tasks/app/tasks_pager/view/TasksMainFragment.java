package task.dst.com.tasks.app.tasks_pager.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import task.dst.com.tasks.BaseFragment;
import task.dst.com.tasks.R;
import task.dst.com.tasks.app.task_new.view.NewTaskFragment;
import task.dst.com.tasks.app.tasks_pager.adapter.ViewPagerAdapter;
import task.dst.com.tasks.app.tasks_pager.presenter.TasksMainPresenterImpl;
import task.dst.com.tasks.core.UtilInterface;
import task.dst.com.tasks.databinding.FragmentTasksMainBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksMainFragment extends BaseFragment<TasksMainPresenterImpl> implements TasksMainView, UtilInterface, View.OnClickListener {

    private FragmentTasksMainBinding binding;
    private List<Fragment> fragmentList;

    public TasksMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tasks_main, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        presenterView = new TasksMainPresenterImpl(this);
        setUpPager();
        binding.taskCreate.setOnClickListener(this);
    }

    private void setUpPager() {
        binding.tabs.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(2);
        fragmentList = presenterView.getFragments();
        binding.pager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragmentList, presenterView.getTabTitles()));
    }

    @Override
    public void onClick(View v) {
        ((UtilInterface) getActivity()).switchFragmentAddToBackStack(new NewTaskFragment());
    }

    @Override
    public void refreshTaskList() {
        ((UtilInterface) fragmentList.get(0)).refreshTaskList();
    }
}
