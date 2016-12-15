package com.taskr.taskr.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taskr.taskr.MainActivity;
import com.taskr.taskr.R;
import com.taskr.taskr.adapters.TaskRecyclerViewAdapter;
import com.taskr.taskr.models.Task;

import java.util.ArrayList;

public class TaskFragment extends Fragment {

    private MainActivity mainActivity;
    private ArrayList<Task> mTasks = new ArrayList<>();
    private TaskRecyclerViewAdapter taskRecyclerViewAdapter;
    private boolean manual;

    public TaskFragment() {}

    @SuppressLint("ValidFragment")
    public TaskFragment(MainActivity mainActivity, boolean manual) {
        this.mainActivity = mainActivity;
        this.manual = manual;
    }

    public void initialize(MainActivity mainActivity, boolean manual) {
        this.mainActivity = mainActivity;
        this.manual = manual;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskRecyclerViewAdapter = new TaskRecyclerViewAdapter(getContext(), mTasks);
        updateTasks();
    }

    public void updateTasks() {
        mTasks.clear();
        if (mainActivity != null) {
            if (manual) {
                mTasks.addAll(mainActivity.getManualTasks());
            } else {
                mTasks.addAll(mainActivity.getAutomaticTasks());
            }
            taskRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setAdapter(taskRecyclerViewAdapter);
        }
        return view;
    }

}
