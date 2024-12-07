package com.shajib.interactivelearningforkids;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initializing views
        emailEditText = findViewById(R.id.passwordResetEmailEditTextId);
        resetPasswordButton = findViewById(R.id.passwordResetButtonId);
        progressBar = findViewById(R.id.progressbarId);

        // Setting listeners
        resetPasswordButton.setOnClickListener(this);

        // Back button listener to return to SignInActivity
        findViewById(R.id.backFromResetPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the SignInActivity
                finish(); // Finish the current activity and return to the previous one
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.passwordResetButtonId) {
            resetPassword();
        }
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();

        // Input validation
        if (email.isEmpty()) {
            emailEditText.setError("Enter an email address");
            emailEditText.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            emailEditText.requestFocus();
            return;
        }

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Firebase password reset
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(ResetPassword.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                        // Navigate back to SignInActivity after successful password reset request
                        finish();
                    } else {
                        Toast.makeText(ResetPassword.this, "Failed to send reset email. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
