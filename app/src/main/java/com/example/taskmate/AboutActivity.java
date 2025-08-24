package com.example.taskmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for this activity
        setContentView(R.layout.activity_about);

        // Find the Toolbar defined in the layout
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Set the Toolbar as the ActionBar for this activity
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource (main_menu.xml) into the Toolbar
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true; // return true to display the menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle menu item clicks
        int id = item.getItemId();

        if (id == R.id.home) {
            // If the "Home" menu item is clicked
            Toast.makeText(this, "Main Activity", Toast.LENGTH_SHORT).show(); // show a short message
            // Start the MainActivity
            startActivity(new Intent(this, MainActivity.class));
            return true; // event handled
        } else if (id == R.id.menu_settings) {
            // If the "Settings" menu item is clicked
            Intent i = new Intent(this, SettingActivity.class); // create an intent to start SettingActivity
            startActivity(i); // start the Settings activity
            return true; // event handled
        }

        // If the menu item is not handled here, pass it to the superclass
        return super.onOptionsItemSelected(item);
    }
}
