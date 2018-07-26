package task.dst.com.tasks.app.task_new.view;


import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import task.dst.com.tasks.BaseFragment;
import task.dst.com.tasks.R;
import task.dst.com.tasks.app.task_new.model.UserLists;
import task.dst.com.tasks.app.task_new.presenter.NewTaskPresenterImpl;
import task.dst.com.tasks.app.tasks.model.AllTasksResponse;
import task.dst.com.tasks.app.tasks.model.TaskStatus;
import task.dst.com.tasks.databinding.DatePickerLayoutBinding;
import task.dst.com.tasks.databinding.FragmentNewTaskBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewTaskFragment extends BaseFragment<NewTaskPresenterImpl> implements NewTaskView {

    private FragmentNewTaskBinding binding;
    private long time;

    public NewTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_task, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        presenterView = new NewTaskPresenterImpl(this);
        presenterView.getUsers();

        binding.done.setOnClickListener(v -> {
            presenterView.createNewTask(new AllTasksResponse(binding.newTaskName.getText().toString(), binding.newTaskContent.getText().toString(), TaskStatus.OPEN, time));
        });

        binding.dueDate.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                showDatePickerDialog(v);
            }
            return false;
        });
    }

    private void showDatePickerDialog(View view) {
        final Dialog dialog = new Dialog(binding.getRoot().getContext());
        DatePickerLayoutBinding pickerLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.date_picker_layout, null, false);
        dialog.setContentView(pickerLayoutBinding.getRoot());

        Calendar calendar = Calendar.getInstance();

        pickerLayoutBinding.pickerDone.setOnClickListener(v -> {
            calendar.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), pickerLayoutBinding.picker.getCurrentHour(), pickerLayoutBinding.picker.getCurrentMinute(), 0);
            time = calendar.getTimeInMillis();
            ((AppCompatEditText) view).setText(new SimpleDateFormat("yyy/MM/dd HH:ss", Locale.ENGLISH).format(calendar.getTimeInMillis()));
        });

        dialog.show();
    }

    @Override
    public void onGetUsers(UserLists userListResponse) {
        binding.usersSpinner.setAdapter(new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_list_item_1, userListResponse.getUsers()));

        binding.usersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenterView.onUserSelected(userListResponse.getUserList().get(parent.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
