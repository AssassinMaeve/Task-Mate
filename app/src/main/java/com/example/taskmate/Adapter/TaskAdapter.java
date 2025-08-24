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

/**
 * TaskAdapter - A custom RecyclerView Adapter for displaying and managing Task items.
 * Each task item contains:
 * - A title (TextView)
 * - A checkbox (to mark completed or not)
 * - A delete button
 *
 * The adapter uses a listener (OnTaskActionListener) to communicate task events
 * like completing, deleting, or editing back to the Activity/Fragment.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<Task> tasks; // List of all tasks
    private OnTaskActionListener listener; // Listener to handle user actions

    // Constructor to initialize the adapter with task list and listener
    public TaskAdapter(ArrayList<Task> tasks, OnTaskActionListener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    /**
     * Creates a new ViewHolder object when there are no existing ViewHolders
     * available that the RecyclerView can reuse.
     */
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate (create) a new view from the XML layout (item_task.xml)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(v);
    }

    /**
     * Binds data to a ViewHolder. This is where you set the values for each task item.
     */
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position); // Get the current task

        // Set task title and completion state
        holder.textTask.setText(task.getTitle());
        holder.checkBox.setChecked(task.isCompleted());

        // Handle checkbox toggle (mark complete/incomplete)
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked); // Update task status
            if (listener != null) {
                listener.onTaskCompleted(task, isChecked); // Notify activity/fragment
            }
        });

        // Handle delete button click
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskDeleted(task, holder.getAdapterPosition()); // Notify deletion
            }
        });

        // Handle clicking anywhere on the task item (for editing)
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskEdited(tasks.get(position), position); // Notify edit
            }
        });
    }

    /**
     * Returns the total number of tasks (used by RecyclerView to know how many items).
     */
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /**
     * Add a new task to the list and update RecyclerView.
     */
    public void addTask(Task task) {
        tasks.add(task);
        notifyItemInserted(tasks.size() - 1); // Notify RecyclerView of insertion
    }

    /**
     * Remove a task from the list at the given position and update RecyclerView.
     */
    public void removeTask(int position) {
        tasks.remove(position);
        notifyItemRemoved(position); // Notify RecyclerView of removal
    }

    /**
     * TaskViewHolder - Holds references to the UI elements for each task item.
     * This avoids calling findViewById repeatedly, improving performance.
     */
    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textTask;     // Task title
        CheckBox checkBox;     // Checkbox for completion
        ImageButton btnDelete; // Delete button

        TaskViewHolder(View itemView) {
            super(itemView);
            textTask = itemView.findViewById(R.id.textTask);
            checkBox = itemView.findViewById(R.id.checkBox);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    /**
     * Callback interface to handle actions on tasks (completed, deleted, edited).
     * The Activity/Fragment must implement this interface to receive events.
     */
    public interface OnTaskActionListener {
        void onTaskCompleted(Task task, boolean isCompleted);
        void onTaskDeleted(Task task, int position);
        void onTaskEdited(Task task, int position);
    }
}
