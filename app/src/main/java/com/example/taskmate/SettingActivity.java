package com.example.taskmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

public class SettingActivity extends AppCompatActivity {

    // Button to toggle dark/light mode
    Button btnToggle;

    // SharedPreferences to save user settings
    SharedPreferences sharedPreferences;

    // Boolean to track current theme
    boolean isDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize SharedPreferences to store settings
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean("dark_mode", false); // retrieve saved theme

        // Set up Toolbar as ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Apply theme before setting the layout
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // dark mode
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // light mode
        }

        // Set layout for this activity
        setContentView(R.layout.activity_setting);

        // Find the toggle button in layout
        btnToggle = findViewById(R.id.btnToggle);

        // Set initial button text depending on current theme
        btnToggle.setText(isDarkMode ? "Switch to Light Mode" : "Switch to Dark Mode");

        // Toggle dark/light mode on button click
        btnToggle.setOnClickListener(v -> {
            isDarkMode = !isDarkMode; // switch theme value

            // Save the new theme preference
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("dark_mode", isDarkMode);
            editor.apply();

            // Apply theme immediately
            AppCompatDelegate.setDefaultNightMode(
                    isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );

            recreate(); // Reload activity to reflect theme change
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate toolbar menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle toolbar menu item clicks
        int id = item.getItemId();

        if (id == R.id.menu_about) {
            // Navigate to AboutActivity
            Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (id == R.id.menu_settings) {
            // Already in Settings, but restart activity (optional)
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item); // default handling
    }
}