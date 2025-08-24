package com.example.taskmate.Model;

public class TaskModel {
    private String task;
    private boolean completed;

    public TaskModel(String task, boolean completed) {
        this.task = task;
        this.completed = completed;
    }

    public String getTask() {
        return task;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
