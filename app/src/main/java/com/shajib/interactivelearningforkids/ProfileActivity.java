package com.shajib.interactivelearningforkids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView profileName, profileEmail, profileMarks;
    private TextView profileEmailTextView;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileEmailTextView = findViewById(R.id.profile_email_singIn_or_singUp);

        // Fetch email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);

        // Get userId from Firebase Auth (current user's unique ID)
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Initialize database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize views
        toolbar = findViewById(R.id.toolbar);
        profileName = findViewById(R.id.profile_name);
        profileEmail = findViewById(R.id.profile_email);
        profileMarks = findViewById(R.id.profile_marks);

        // Set up the toolbar as ActionBar
        setSupportActionBar(toolbar);

        // Set email in the profileEmailTextView
        if (email != null) {
            profileEmailTextView.setText("Email: " + email);
            fetchUserData(userId); // Fetch data using the userId
        } else {
            Toast.makeText(this, "No email found", Toast.LENGTH_SHORT).show();
        }

        // Set up the home button functionality
        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(view -> {
            // Navigate to MainActivity when home button is clicked
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void fetchUserData(String userId) {
        // Fetch user data from the database using the userId
        databaseReference.child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String marks = dataSnapshot.child("marks").getValue(String.class);

                    // Update the TextViews with the fetched data
                    profileName.setText(name != null ? name : "None");
                    profileEmail.setText(email != null ? email : "None");
                    profileMarks.setText(marks != null ? marks : "None");
                } else {
                    Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to fetch data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);

        // Find the Sign Out menu item
        MenuItem signOutItem = menu.findItem(R.id.signOutMenuId);

        // Create a SpannableString to change the color of the text
        SpannableString spannableString = new SpannableString(signOutItem.getTitle());

        // Apply color to the text (R.color.sign_out_text_color is the color resource)
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.sign_out_text_color)),
                0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the colored title
        signOutItem.setTitle(spannableString);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.signOutMenuId) {
            // Clear SharedPreferences to log out
            SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("email");
            editor.apply();

            // Sign out and navigate to the main activity
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}