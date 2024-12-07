package com.shajib.interactivelearningforkids;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    private ProgressBar progressBar;
    private TextView checkingEmailTextView;

    private DatabaseReference databaseReference; // Firebase Realtime Database reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.setTitle("Sign Up");

        // Initialize UI elements
        nameEditText = findViewById(R.id.signUpNameEditTextId);
        emailEditText = findViewById(R.id.signUpEmailEditTextId);
        passwordEditText = findViewById(R.id.signUpPasswordEditTextId);
        confirmPasswordEditText = findViewById(R.id.signUpConfirmPasswordEditTextId);
        signUpButton = findViewById(R.id.signUpButtonId);
        progressBar = findViewById(R.id.progressbarId);

        // Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Set listener
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signUpButtonId) {
            userSignUp();
        }
    }

    private void userSignUp() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim().toLowerCase();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validate input
        if (name.isEmpty()) {
            nameEditText.setError("Enter your name");
            nameEditText.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            emailEditText.setError("Enter your email address");
            emailEditText.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Enter your password");
            passwordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            passwordEditText.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            confirmPasswordEditText.requestFocus();
            return;
        }

        // Check network connection
        if (!isNetworkAvailable()) {
            Toast.makeText(SignUpActivity.this, "No internet connection. Please check your network.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress bar and disable UI elements
        progressBar.setVisibility(View.VISIBLE);
        signUpButton.setEnabled(false);
        // Check if the email is already registered using Firebase Realtime Database
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                signUpButton.setEnabled(true);

                if (dataSnapshot.exists()) {
                    // Email already registered
                    emailEditText.setText("");  // Clear the email field
                    emailEditText.setError("This email is already registered");
                    emailEditText.requestFocus();
                } else {
                    // Email not registered, proceed with OTP generation
                    generateOtpAndSendEmail(name, email, password);
                    Intent intent = new Intent(SignUpActivity.this, OtpActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                signUpButton.setEnabled(true);
                // Handle failure (e.g., network issue)
                Toast.makeText(SignUpActivity.this, "Failed to check email. Please try again.", Toast.LENGTH_SHORT).show();
                Log.e("SignUpActivity", "Error checking email: ", databaseError.toException());
            }
        });
    }

    private void generateOtpAndSendEmail(String name, String email, String password) {
        Random random = new Random();
        String generatedOtp = String.valueOf(1000 + random.nextInt(9000));

        // Save OTP, name, email, and password for later verification in OtpActivity
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("otp", generatedOtp);
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("password", password); // Store the password
        editor.apply();

        // Send OTP via email using JavaMail API
        new Thread(() -> {
            try {
                // Email Configuration
                String host = "smtp.gmail.com";
                String senderEmail = "shajib.cse.53@gmail.com"; // Replace with your email
                String senderPassword = "wkyz auhu ndon uwjn"; // Replace with your app password
                String subject = "Your OTP Code";
                String message = "Your OTP code is: " + generatedOtp;

                // Properties for SSL
                Properties properties = new Properties();
                properties.put("mail.smtp.host", host);
                properties.put("mail.smtp.port", "465");
                properties.put("mail.smtp.ssl.enable", "true");
                properties.put("mail.smtp.auth", "true");

                // Session
                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderPassword);
                    }
                });

                // Email Message
                Message mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(senderEmail));
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(message);

                // Send Email
                Transport.send(mimeMessage);
                runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "OTP sent to " + email, Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Failed to send OTP. Please try again.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    // Helper method to check network connectivity
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
