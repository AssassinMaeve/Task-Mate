package com.example.taskmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmate.Adapter.TaskAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // UI components
    EditText editTask;
    Button btnAdd;
    RecyclerView recyclerView;

    // Adapter for RecyclerView
    TaskAdapter adapter;

    // List to store tasks
    ArrayList<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for MainActivity
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editTask = findViewById(R.id.editTask);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set layout manager for RecyclerView

        // Set up the toolbar as ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize RecyclerView adapter and define action listeners
        adapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskActionListener() {
            @Override
            public void onTaskCompleted(Task task, boolean isCompleted) {
                // Triggered when a task is marked as completed
                Toast.makeText(MainActivity.this, "Task completed: " + task.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTaskDeleted(Task task, int position) {
                // Triggered when a task is deleted
                adapter.removeTask(position); // remove task from adapter
                Toast.makeText(MainActivity.this, "Task deleted: " + task.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTaskEdited(Task task, int position) {
                // Triggered when a task edit is requested
                showEditTaskDialog(task); // open edit dialog
            }
        });

        recyclerView.setAdapter(adapter); // Set the adapter to RecyclerView

        // Add Task button click listener
        btnAdd.setOnClickListener(v -> {
            String task = editTask.getText().toString(); // get task text from EditText
            if (!task.isEmpty()) {
                taskList.add(new Task(task)); // add new task to list
                adapter.notifyItemInserted(taskList.size() - 1); // notify adapter of new item
                Toast.makeText(MainActivity.this, "Task added: " + task, Toast.LENGTH_SHORT).show();
                editTask.setText(""); // clear input field
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items into toolbar
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true; // show menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle toolbar menu item clicks
        int id = item.getItemId();

        if (id == R.id.menu_about) {
            Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AboutActivity.class)); // navigate to AboutActivity
            return true;
        } else if (id == R.id.menu_settings) {
            startActivity(new Intent(this, SettingActivity.class)); // navigate to SettingActivity
            return true;
        }

        return super.onOptionsItemSelected(item); // default handling
    }

    // Function to show a dialog to edit a task
    private void showEditTaskDialog(Task task) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_task, null); // inflate dialog layout

        EditText editTask = dialogView.findViewById(R.id.editTaskText);
        editTask.setText(task.getTitle()); // set current task title

        new AlertDialog.Builder(this)
                .setTitle("Edit Task")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newText = editTask.getText().toString().trim(); // get updated text
                    if (!newText.isEmpty()) {
                        task.setTitle(newText); // update task
                        adapter.notifyDataSetChanged(); // refresh RecyclerView
                        Toast.makeText(this, "Task updated!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()) // close dialog
                .create()
                .show();
    }
}
