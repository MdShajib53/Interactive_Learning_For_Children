package com.shajib.interactivelearningforkids;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import com.google.android.material.navigation.NavigationView;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<Integer, Class<?>> cardViewMap;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the drawer layout and set up the toolbar
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Open drawer when the Menu icon is clicked
        findViewById(R.id.menuId).setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Initialize the map and populate it with CardView ID and Activity class pairs
        cardViewMap = new HashMap<>();
        cardViewMap.put(R.id.alphabets, AlphabetsActivity.class);
        cardViewMap.put(R.id.numbers, NumbersActivity.class);
        cardViewMap.put(R.id.days, DaysActivity.class);
        cardViewMap.put(R.id.months, MonthsActivity.class);
        cardViewMap.put(R.id.birds, BirdsActivity.class);
        cardViewMap.put(R.id.animals, AnimalsActivity.class);
        cardViewMap.put(R.id.fruits, FruitsActivity.class);
        cardViewMap.put(R.id.crops, CropsActivity.class);
        cardViewMap.put(R.id.flowers, FlowersActivity.class);
        cardViewMap.put(R.id.organs, OrgansActivity.class);
        cardViewMap.put(R.id.quiz, QuizActivity.class);
        cardViewMap.put(R.id.quiz2, Quiz2Activity.class);

        // Loop through the map and set click listeners for each CardView
        for (Map.Entry<Integer, Class<?>> entry : cardViewMap.entrySet()) {
            CardView cardView = findViewById(entry.getKey());
            cardView.setOnClickListener(v -> navigateToAnotherClass(v.getId()));
        }

        // Set up the navigation drawer item click listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_signin) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            } else if (item.getItemId() == R.id.nav_signup) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            } else if (item.getItemId() == R.id.nav_profile) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            } else if (item.getItemId() == R.id.nav_exit) {
                finish(); // Exit the activity
            } else {
                // Handle other cases if necessary
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    // Method to navigate to another class based on CardView ID
    private void navigateToAnotherClass(int id) {
        // Check if the user is signed in (you can use SharedPreferences to store sign-in info)
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email", "");

        // If user clicks on the Quiz card, check if the user is signed in
        if (id == R.id.quiz || id == R.id.quiz2) {
            if (userEmail.isEmpty()) {
                // Show a Toast message if user is not signed in
                Toast.makeText(this, "Sign in to access the Quiz", Toast.LENGTH_SHORT).show();
            } else {
                // If user is signed in, navigate to the Quiz activity
                Class<?> targetActivity = cardViewMap.get(id);
                if (targetActivity != null) {
                    Intent intent = new Intent(this, targetActivity);
                    startActivity(intent);
                }
            }
        } else {
            // For other CardViews, navigate as usual
            Class<?> targetActivity = cardViewMap.get(id);
            if (targetActivity != null) {
                Intent intent = new Intent(this, targetActivity);
                startActivity(intent);
            }
        }
    }
}
