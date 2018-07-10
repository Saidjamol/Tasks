package task.dst.com.tasks.app.tasks.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import task.dst.com.tasks.R;
import task.dst.com.tasks.app.tasks.model.TaskStatus;
import task.dst.com.tasks.app.tasks.presenter.TasksPresenterImpl;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {

    private final TasksPresenterImpl tasksPresenter;
    private Context context;

    public TaskListAdapter(Context context, TasksPresenterImpl tasksPresenter) {
        this.tasksPresenter = tasksPresenter;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        tasksPresenter.onBindTaskAtPosition(position, holder);
    }


    @Override
    public int getItemCount() {
        return tasksPresenter.getTaskListCount();
    }

    public class TaskListViewHolder extends RecyclerView.ViewHolder implements TaskListItemView {
        TextView taskName, taskMsg, taskTime;
        AppCompatImageView task_status;

        TaskListViewHolder(View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.task_name);
            taskMsg = itemView.findViewById(R.id.task_msg);
            taskTime = itemView.findViewById(R.id.task_time);
            task_status = itemView.findViewById(R.id.task_status);

            itemView.setOnClickListener(v -> {
                tasksPresenter.onItemClickedAtPosition(getAdapterPosition());
            });
        }

        @Override
        public void setTaskName(String taskName) {
            this.taskName.setText(taskName);
        }

        @Override
        public void setTaskMsg(String taskMsg) {
            this.taskMsg.setText(taskMsg);
        }

        @Override
        public void setTaskTime(String taskTime) {
            this.taskTime.setText(taskTime);
        }

        @Override
        public void setTaskStatus(TaskStatus status) {
//            task_status.setColorFilter(context.getResources().getColor(status ? R.color.colorRed : R.color.colorGreen));
            switch (status) {
                case OPEN:
                    task_status.setColorFilter(context.getResources().getColor(R.color.colorRed));
                    break;
                case CLOSED:
                    task_status.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                    break;
                case EXPIRED:
                    task_status.setColorFilter(context.getResources().getColor(R.color.colorOrange));
                    break;
            }
        }

    }
}
