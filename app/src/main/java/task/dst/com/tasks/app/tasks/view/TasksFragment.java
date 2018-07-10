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
import task.dst.com.tasks.app.task_new.view.NewTaskFragment;
import task.dst.com.tasks.app.tasks.adapter.TaskListAdapter;
import task.dst.com.tasks.app.tasks.model.AllTasksResponse;
import task.dst.com.tasks.app.tasks.presenter.TasksPresenterImpl;
import task.dst.com.tasks.core.UtilInterface;
import task.dst.com.tasks.databinding.FragmentTasksBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends BaseFragment<TasksPresenterImpl> implements TasksView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

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
        binding.taskList.setNestedScrollingEnabled(false);
        binding.taskList.getLayoutManager().setItemPrefetchEnabled(true);
        binding.taskList.addItemDecoration(new DividerItemDecoration(binding.getRoot().getContext(), DividerItemDecoration.VERTICAL));
        binding.refresh.setOnRefreshListener(this);
        binding.taskCreate.setOnClickListener(this);

        presenterView.getTasks();
        presenterView.startTaskSevice(getContext());

        LocalBroadcastManager.getInstance(binding.getRoot().getContext()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                AllTasksResponse response = (AllTasksResponse) intent.getSerializableExtra("TASK");
//                ((TaskListAdapter) binding.taskList.getAdapter()).update(response);
                presenterView.updateTaskList(response);
            }
        }, new IntentFilter("MESSAGE"));
    }

    @Override
    public void onSuccess(List<AllTasksResponse> response) {
        binding.taskList.setAdapter(new TaskListAdapter(getContext(), presenterView));
    }

    @Override
    public void openTaskDetails(String taskId) {
        final Bundle bundle = new Bundle();
        bundle.putString("taskId", taskId);
        TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();
        taskDetailsFragment.setArguments(bundle);
        ((UtilInterface) getActivity()).switchFragmentAddToBackStack(taskDetailsFragment);
    }

    @Override
    public void onRefresh() {
        presenterView.getTasks();
    }

    @Override
    public void setRefreshView(boolean enable) {
        binding.refresh.setRefreshing(enable);
    }

//    @Override
//    public void onGetUsers(UserLists userListResponse) {
//        usersSpinner.setAdapter(new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_list_item_1, userListResponse.getUsers()));
//
//        usersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                presenterView.onUserSelected(userListResponse.getUserList().get(parent.getSelectedItem()));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    @Override
    public void refreshTaskAdapter() {
        binding.taskList.getAdapter().notifyItemInserted(0);
    }

    @Override
    public void onClick(View v) {
//        final Dialog dialog = new Dialog(binding.getRoot().getContext());
//        NewTaskDialogLayoutBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.date_picker_layout, null, false);
//        dialog.setContentView(dialogBinding.getRoot());
//        Window window = dialog.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        usersSpinner = dialogBinding.usersSpinner;
//        presenterView.getUsers();
//
//        dialogBinding.done.setOnClickListener(v1 -> {
//            if (presenterView.createNewTask(new AllTasksResponse(dialogBinding.newTaskName.getText().toString(), dialogBinding.newTaskContent.getText().toString(), TaskStatus.OPEN))) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
        ((UtilInterface) getActivity()).switchFragmentAddToBackStack(new NewTaskFragment());
    }
}
