package com.shajib.interactivelearningforkids;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrgansActivity extends AppCompatActivity {

    private ImageView imageViewFullOrgan, backFromOrgansActivity;
    private TextView textViewOrganName;
    private Button buttonPreviousOrgan, buttonNextOrgan;
    private MediaPlayer mediaPlayer;

    // Organ images
    private int[] organImages = {
            R.drawable.hand,
            R.drawable.arm,
            R.drawable.ears,
            R.drawable.eye,
            R.drawable.fingers,
            R.drawable.legs,
            R.drawable.hair,
            R.drawable.lips,
            R.drawable.nose,
            R.drawable.teeth
    };

    // Organ sounds (optional, replace with actual files if available)
    private int[] organSounds = {
            R.raw.hand,
            R.raw.arm,
            R.raw.ears,
            R.raw.eye,
            R.raw.fingers,
            R.raw.legs,
            R.raw.hair,
            R.raw.lips,
            R.raw.nose,
            R.raw.teeth
    };

    // Organ names
    private String[] organNames = {
            "Hand",
            "Arm",
            "Ears",
            "Eye",
            "Fingers",
            "Legs",
            "Hair",
            "Lips",
            "Nose",
            "Teeth"
    };

    private int currentIndex = 0; // Track the current organ index

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organs);

        // Initialize views
        imageViewFullOrgan = findViewById(R.id.imageViewFullOrgan);
        textViewOrganName = findViewById(R.id.textViewOrganName);
        buttonPreviousOrgan = findViewById(R.id.buttonPreviousOrgan);
        buttonNextOrgan = findViewById(R.id.buttonNextOrgan);
        backFromOrgansActivity = findViewById(R.id.backFromOrgansActivity); // Back button

        // Load the first organ
        loadOrgan(currentIndex);

        // Set button listeners
        buttonPreviousOrgan.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                loadOrgan(currentIndex);
            }
        });

        buttonNextOrgan.setOnClickListener(v -> {
            if (currentIndex < organImages.length - 1) {
                currentIndex++;
                loadOrgan(currentIndex);
            }
        });

        // Back button listener
        backFromOrgansActivity.setOnClickListener(v -> finish());  // Close current activity and go back
    }

    private void loadOrgan(int index) {
        imageViewFullOrgan.setImageResource(organImages[index]);
        textViewOrganName.setText(organNames[index]);
        playSound(organSounds[index]);
    }

    private void playSound(int soundResourceId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
