package com.shajib.interactivelearningforkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText, passwordEditText;
    private TextView signUpTextView, forgotPasswordTextView;
    private Button signInButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.setTitle("Sign In");

        mAuth = FirebaseAuth.getInstance();

        // Initializing views
        emailEditText = findViewById(R.id.signInEmailEditTextId);
        passwordEditText = findViewById(R.id.signInPasswordEditTextId);
        signInButton = findViewById(R.id.signInButtonId);
        signUpTextView = findViewById(R.id.signUpTextViewId);
        forgotPasswordTextView = findViewById(R.id.forgotPassword);
        progressBar = findViewById(R.id.progressbarId);

        // Setting listeners
        signInButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);
        forgotPasswordTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.signInButtonId) {
            userLogin();
        } else if (id == R.id.signUpTextViewId) {
            // Navigate to Sign Up Activity
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        } else if (id == R.id.forgotPassword) {
            // Navigate to Reset Password Activity
            Intent intent = new Intent(SignInActivity.this, ResetPassword.class);
            startActivity(intent);
        }
    }

    private void userLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Input validations
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

        if (password.isEmpty()) {
            passwordEditText.setError("Enter a password");
            passwordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            passwordEditText.requestFocus();
            return;
        }

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Firebase sign-in
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    // Save user email to SharedPreferences for persistent login
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.apply();

                    // Navigate to Profile Activity
                    Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignInActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
