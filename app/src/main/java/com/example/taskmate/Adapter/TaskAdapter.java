package com.example.taskmate.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmate.R;
import com.example.taskmate.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<Task> tasks;
    private OnTaskActionListener listener;

    // Constructor
    public TaskAdapter(ArrayList<Task> tasks, OnTaskActionListener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textTask.setText(task.getTitle());
        holder.checkBox.setChecked(task.isCompleted());

        // Checkbox (completed)
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            if (listener != null) {
                listener.onTaskCompleted(task, isChecked);
            }
        });

        // Delete button
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskDeleted(task, holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskEdited(tasks.get(position), position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addTask(Task task) {
        tasks.add(task);
        notifyItemInserted(tasks.size() - 1);
    }

    public void removeTask(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textTask;
        CheckBox checkBox;
        ImageButton btnDelete;

        TaskViewHolder(View itemView) {
            super(itemView);
            textTask = itemView.findViewById(R.id.textTask);
            checkBox = itemView.findViewById(R.id.checkBox);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    // Callback interface
    public interface OnTaskActionListener {
        void onTaskCompleted(Task task, boolean isCompleted);
        void onTaskDeleted(Task task, int position);
        void onTaskEdited(Task task, int position);
    }
}
