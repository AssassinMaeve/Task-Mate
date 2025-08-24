# 📋 TaskMate – To-Do List App

TaskMate is a simple **Android To-Do List app** built in Java using **Android Studio**
It lets you add tasks, mark them as completed, edit tasks using a dialog, and navigate between activities with menus.

This project is designed as a **beginner-friendly learning app** for students to understand Android development concepts such as

* Activities
* RecyclerView & Adapters
* Menus (Toolbar)
* Dialogs
* Model classes

---

## 🚀 Features

* Add new tasks
* Edit tasks using a **custom dialog**
* Mark tasks as completed
* Delete tasks (optional to add)
* Navigation menu with **About** and **Settings**
* Simple **black & white theme** for clarity

---

## 📂 Project Structure

```
app/src/main/java/com/example/taskmate/
│
├── MainActivity.java        # Main screen with task list
├── AboutActivity.java       # About page
├── SettingActivity.java     # Settings page
├── Task.java                # Model class for Task
├── TaskAdapter.java         # RecyclerView Adapter for tasks
```

---

## 🛠️ Setup Instructions

### 1. Clone the Project

```bash
git clone https://github.com/yourusername/taskmate.git
```

### 2. Open in Android Studio

* Open Android Studio
* Select **Open an Existing Project**
* Choose the `taskmate` folder

### 3. Build & Run

* Connect an emulator or physical device
* Click ▶️ **Run** in Android Studio

---

## 🖼️ Screenshots (Optional to add later)

* Home Screen
* Edit Task Dialog
* About Page

---

## 🧩 Code Walkthrough

### Task Model (`Task.java`)

```java
public class Task {
    private String title;
    private boolean completed;

    public Task(String title) {
        this.title = title;
        this.completed = false;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }   // used in edit
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
```

### Edit Task Dialog (Inside `TaskAdapter`)

```java
AlertDialog.Builder builder = new AlertDialog.Builder(context);
builder.setTitle("Edit Task");

final EditText input = new EditText(context);
input.setText(task.getTitle());  // show current title
builder.setView(input);

builder.setPositiveButton("Save", (dialog, which) -> {
    String newText = input.getText().toString().trim();
    if (!newText.isEmpty()) {
        task.setTitle(newText);   // update task title
        notifyItemChanged(position);
    }
});
builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

builder.show();
```

---

## 🎓 Learning Outcomes

By working with TaskMate, students will learn:

* How to create and switch between **Activities**
* How to use **RecyclerView with Adapters**
* How to build a **custom dialog** for editing tasks
* How to define **app themes** (black & white for simplicity)
* How to implement a **menu in the toolbar**

---

## 📖 Possible Extensions

* Add task **due dates**
* Add **SQLite / Room Database** for persistence
* Add **Dark Mode toggle** in Settings
* Add **Categories / Tags** for tasks

---

## 📝 License

This project is for **educational purposes**.
Students are free to modify, extend, and use it in their own projects.

---
