package task.dst.com.tasks.app.task_details.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import task.dst.com.tasks.BaseFragment;
import task.dst.com.tasks.R;
import task.dst.com.tasks.app.task_details.model.TaskDetailsResponse;
import task.dst.com.tasks.app.task_details.presenter.TaskDetailsPresenter;
import task.dst.com.tasks.app.task_details.presenter.TaskDetailsPresenterImpl;
import task.dst.com.tasks.core.BasePresenterImpl;
import task.dst.com.tasks.databinding.FragmentTaskDetailsBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailsFragment extends BaseFragment<TaskDetailsPresenterImpl> implements TaskDetailsView {

    private FragmentTaskDetailsBinding binding;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_details, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        presenterView = new TaskDetailsPresenterImpl(this);
        presenterView.getTaskDetails();

        binding.taskDoneBtn.setOnClickListener(v -> {
            presenterView.closeTask();
        });
    }

    @Override
    public void onSuccess(TaskDetailsResponse response) {
        binding.taskName.setText(response.getTaskName());
        binding.taskMsg.setText(response.getContent());
        binding.taskTime.setText(BasePresenterImpl.parseTime(response.getTimeStamp()));
    }

    @Override
    public String getTaskId() {
        return getArguments() != null ? getArguments().getString("taskId") : null;
    }
}
