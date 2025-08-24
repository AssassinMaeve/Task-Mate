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

    EditText editTask;
    Button btnAdd;
    RecyclerView recyclerView;
    TaskAdapter adapter;
    ArrayList<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTask = findViewById(R.id.editTask);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // ✅ Initialize adapter inside onCreate
        adapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskActionListener() {
            @Override
            public void onTaskCompleted(Task task, boolean isCompleted) {
                Toast.makeText(MainActivity.this, "Task completed: " + task.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTaskDeleted(Task task, int position) {
                adapter.removeTask(position);
                Toast.makeText(MainActivity.this, "Task deleted: " + task.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTaskEdited(Task task, int position) {
                showEditTaskDialog(task);
            }

        });

        recyclerView.setAdapter(adapter);

        // ✅ Add task button
        btnAdd.setOnClickListener(v -> {
            String task = editTask.getText().toString();
            if (!task.isEmpty()) {
                taskList.add(new Task(task));
                adapter.notifyItemInserted(taskList.size() - 1);
                Toast.makeText(MainActivity.this, "Task added: " + task, Toast.LENGTH_SHORT).show();
                editTask.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (item.getItemId() == R.id.menu_settings) {
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showEditTaskDialog(Task task) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_task, null);

        EditText editTask = dialogView.findViewById(R.id.editTaskText);
        editTask.setText(task.getTitle());

        new AlertDialog.Builder(this)
                .setTitle("Edit Task")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newText = editTask.getText().toString().trim();

                    if (!newText.isEmpty()) {
                        task.setTitle(newText);
                        adapter.notifyDataSetChanged(); // 👈 refresh UI
                        Toast.makeText(this, "Task updated!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }



}
