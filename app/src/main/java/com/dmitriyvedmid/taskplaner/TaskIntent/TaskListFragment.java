package com.dmitriyvedmid.taskplaner.TaskIntent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dmitriyvedmid.taskplaner.R;
import com.dmitriyvedmid.taskplaner.Task;
import com.dmitriyvedmid.taskplaner.TaskLab;
import com.dmitriyvedmid.taskplaner.TaskPagerActivity;

import java.util.List;

/**
 * Created by dmitr on 4/22/2017.
 */

public class TaskListFragment extends Fragment {
    public static final int REQUEST_TASK = 1;
    private RecyclerView mTaskRecyclerView;
    private TaskAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstantState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        mTaskRecyclerView = (RecyclerView) view.findViewById(R.id.task_recycler_view);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        TaskLab taskLab = TaskLab.get(getActivity());
        List<Task> tasks = taskLab.getTasks();
        if (mAdapter == null) {
            mAdapter = new TaskAdapter(tasks);
            mTaskRecyclerView.setAdapter(mAdapter);
        } else mAdapter.notifyDataSetChanged();
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        private Task mTask;

        public TaskHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_task_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_task_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_task_solved_check_box);
        }

        public void bindTask(Task task) {
            mTask = task;
            mTitleTextView.setText(mTask.getTitle());
            mDateTextView.setText(mTask.getDate().toString());
            mSolvedCheckBox.setChecked(mTask.isSolved());
        }

        @Override
        public void onClick(View v) {
            Intent intent = TaskPagerActivity.newIntent(getActivity(), mTask.getId());
            startActivityForResult(intent, REQUEST_TASK);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TASK) {
            //smth
        }

    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_task, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
}
