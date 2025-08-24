package com.example.taskmate;

// Model class representing a single task
public class Task {

    // Title or description of the task
    private String title;

    // Boolean to track if the task is completed or not
    private boolean completed;

    // Constructor to create a new task with a title
    public Task(String title) {
        this.title = title;       // Set task title
        this.completed = false;   // By default, a new task is not completed
    }

    // Getter for task title
    public String getTitle() {
        return title;
    }

    // Setter for task title (used when editing the task)
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter to check if task is completed
    public boolean isCompleted() {
        return completed;
    }

    // Setter to mark task as completed or not
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
