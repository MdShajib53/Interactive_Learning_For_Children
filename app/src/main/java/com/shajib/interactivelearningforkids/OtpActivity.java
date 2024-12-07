package com.shajib.interactivelearningforkids;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OtpActivity extends AppCompatActivity {

    private EditText otpEditText;
    private Button verifyOtpButton;
    private Button resendOtpButton;
    private TextView timerTextView;
    private ImageView backButton;
    private TextView goToHomeTextView;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String generatedOtp, userEmail, userName, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        // Initialize UI elements
        otpEditText = findViewById(R.id.otpEditTextId);
        verifyOtpButton = findViewById(R.id.verifyOtpButtonId);
        resendOtpButton = findViewById(R.id.resendOtpButtonId);
        timerTextView = findViewById(R.id.timerTextViewId);
        backButton = findViewById(R.id.backButtonId);
        goToHomeTextView = findViewById(R.id.goToHomeTextViewId);
        progressBar = findViewById(R.id.progressBar); // Initialize ProgressBar

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Retrieve OTP, email, name, and password from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        generatedOtp = sharedPreferences.getString("otp", "");
        userEmail = sharedPreferences.getString("email", "");
        userName = sharedPreferences.getString("name", "");
        userPassword = sharedPreferences.getString("password", "");

        // Start the countdown timer
        startTimer();

        // Handle OTP verification
        verifyOtpButton.setOnClickListener(v -> verifyOtp());

        // Handle Resend OTP functionality
        resendOtpButton.setOnClickListener(v -> resendOtp());

        // Handle Back Button
        backButton.setOnClickListener(v -> onBackPressed());

        // Handle Go to Home Link
        goToHomeTextView.setOnClickListener(v -> goToHome());
    }

    // Start countdown timer
    private void startTimer() {
        verifyOtpButton.setEnabled(true);
        verifyOtpButton.setAlpha(1.0f);

        resendOtpButton.setEnabled(false); // Disable Resend OTP button initially
        resendOtpButton.setAlpha(0.5f);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time left: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Time's up!");
                resendOtpButton.setEnabled(true); // Enable Resend OTP button after timer finishes
                resendOtpButton.setAlpha(1.0f);

                verifyOtpButton.setEnabled(false);
                verifyOtpButton.setAlpha(0.5f);
            }
        };
        countDownTimer.start();
    }

    // Handle OTP verification logic
    private void verifyOtp() {
        String enteredOtp = otpEditText.getText().toString().trim();

        if (enteredOtp.isEmpty()) {
            otpEditText.setError("Please enter OTP");
            otpEditText.requestFocus();
            return;
        }

        // Show ProgressBar (loading indicator)
        progressBar.setVisibility(View.VISIBLE);

        // Disable the verify button to prevent multiple clicks
        verifyOtpButton.setEnabled(false);

        if (enteredOtp.equals(generatedOtp)) {
            // OTP verified, proceed with Firebase Auth and Realtime Database
            mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(task -> {
                        // Hide the ProgressBar after completion
                        progressBar.setVisibility(View.GONE);
                        verifyOtpButton.setEnabled(true); // Enable button again

                        if (task.isSuccessful()) {
                            // Save user data in Firebase Realtime Database
                            String userId = mAuth.getCurrentUser().getUid();
                            mDatabase.child(userId).child("name").setValue(userName);
                            mDatabase.child(userId).child("email").setValue(userEmail);

                            // Navigate to SignInActivity after successful OTP verification
                            Toast.makeText(OtpActivity.this, "OTP Verified Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(OtpActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(OtpActivity.this, "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // If OTP is wrong
            progressBar.setVisibility(View.GONE); // Hide the ProgressBar
            verifyOtpButton.setEnabled(true); // Enable button again
            Toast.makeText(this, "Wrong OTP. Please try again.", Toast.LENGTH_SHORT).show();
            otpEditText.setText("");
        }
    }

    // Handle Resend OTP functionality
    private void resendOtp() {
        // Call generateOtpAndSendEmail function to resend the OTP
        generateOtpAndSendEmail(userName, userEmail, userPassword);
        startTimer(); // Restart the countdown timer after resending OTP
    }

    // Generate OTP and send email logic
    private void generateOtpAndSendEmail(String name, String email, String password) {
        Random random = new Random();
        String newgeneratedOtp = String.valueOf(1000 + random.nextInt(9000));
        generatedOtp = newgeneratedOtp;
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

                // Set up session
                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderPassword);
                    }
                });

                // Create and send email message
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(senderEmail));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                msg.setSubject(subject);
                msg.setText(message);
                Transport.send(msg);

                // Success message
                runOnUiThread(() -> Toast.makeText(OtpActivity.this, "OTP sent to email", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Handle back button press
    private void goToHome() {
        startActivity(new Intent(OtpActivity.this, MainActivity.class));
        finish();
    }
}
