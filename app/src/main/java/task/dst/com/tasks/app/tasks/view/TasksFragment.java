package task.dst.com.tasks.app.tasks.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import task.dst.com.tasks.BaseFragment;
import task.dst.com.tasks.R;
import task.dst.com.tasks.app.task_details.view.TaskDetailsFragment;
import task.dst.com.tasks.app.tasks.adapter.TaskListAdapter;
import task.dst.com.tasks.app.tasks.model.AllTasksResponse;
import task.dst.com.tasks.app.tasks.presenter.TasksPresenterImpl;
import task.dst.com.tasks.core.UtilInterface;
import task.dst.com.tasks.databinding.FragmentTasksBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends BaseFragment<TasksPresenterImpl> implements TasksView, SwipeRefreshLayout.OnRefreshListener, UtilInterface {

    private FragmentTasksBinding binding;

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tasks, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        presenterView = new TasksPresenterImpl(this);

        binding.taskList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.taskList.setNestedScrollingEnabled(true);
        binding.taskList.getLayoutManager().setItemPrefetchEnabled(true);
        binding.taskList.addItemDecoration(new DividerItemDecoration(binding.getRoot().getContext(), DividerItemDecoration.VERTICAL));
        binding.refresh.setOnRefreshListener(this);

        if (getArguments() != null) {
            switch (getArguments().getInt("TASK_TAB")) {
                case 0:
                    presenterView.getTasks();
                    break;
                case 1:
                    presenterView.getSentTasks();
                    break;
            }
        }

        presenterView.startTaskSevice(getContext());

//        LocalBroadcastManager.getInstance(binding.getRoot().getContext()).registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                AllTasksResponse response = (AllTasksResponse) intent.getSerializableExtra("TASK");
//                presenterView.updateTaskList(response);
//            }
//        }, new IntentFilter("MESSAGE"));

        IntentFilter filter = new IntentFilter("MESSAGE");
        filter.addAction("TASK_UPDATE");

        BroadcastReceiver ActivityDataReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getArguments() != null) {
                    if (getArguments().getInt("TASK_TAB") == 0) {
                        if (intent.getAction().equals("MESSAGE")) {
                            presenterView.updateTaskList((AllTasksResponse) intent.getSerializableExtra("TASK"));
                        }
                    }
                }

                if (intent.getAction().equals("TASK_UPDATE")) {
                    showToast("TASK_UPDATE");
                    refreshTaskList();
                }
            }
        };

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(ActivityDataReceiver, filter);
    }


    @Override
    public void onSuccess(List<AllTasksResponse> response) {
        binding.taskList.setAdapter(new TaskListAdapter(getContext(), presenterView));
    }

    @Override
    public void openTaskDetails(String taskId) {
        final Bundle bundle = new Bundle();
        bundle.putString("taskId", taskId);
        if (getArguments() != null) {
            bundle.putInt("TASK_TAB", getArguments().getInt("TASK_TAB"));
        }
        TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();
        taskDetailsFragment.setArguments(bundle);
        ((UtilInterface) getActivity()).switchFragmentAddToBackStack(taskDetailsFragment);
    }

    @Override
    public void onRefresh() {
        if (getArguments() != null) {
            switch (getArguments().getInt("TASK_TAB")) {
                case 0:
                    presenterView.getTasks();
                    break;
                case 1:
                    presenterView.getSentTasks();
                    break;
            }
        }
    }

    @Override
    public void setRefreshView(boolean enable) {
        binding.refresh.setRefreshing(enable);
    }

    @Override
    public void refreshTaskAdapter() {
        binding.taskList.getAdapter().notifyItemInserted(0);
    }

    @Override
    public void refreshTaskList() {
        onRefresh();
//        presenterView.getTasks();
    }
}
