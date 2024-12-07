package com.shajib.interactivelearningforkids;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CropsActivity extends AppCompatActivity {

    private ImageView imageViewFullCrop, backFromCropsActivity;
    private TextView textViewCropName;
    private Button buttonPreviousCrop, buttonNextCrop;
    private MediaPlayer mediaPlayer;

    // Crop images
    private int[] cropImages = {
            R.drawable.rice,
            R.drawable.wheat,
            R.drawable.chickpea,
            R.drawable.lentil,
            R.drawable.maize,
            R.drawable.mustard,
            R.drawable.potato,
            R.drawable.jute,
            R.drawable.groundnut,
            R.drawable.sesame
    };

    // Crop sounds (optional, replace with actual files if available)
    private int[] cropSounds = {
            R.raw.rice,
            R.raw.wheat,
            R.raw.chickpea,
            R.raw.lentil,
            R.raw.maize,
            R.raw.mustard,
            R.raw.potato,
            R.raw.jute,
            R.raw.groundnut,
            R.raw.sesame
    };

    // Crop names
    private String[] cropNames = {
            "Rice",
            "Wheat",
            "Chickpea",
            "Lentil",
            "Maize",
            "Mustard",
            "Potato",
            "Jute",
            "Groundnut",
            "Sesame"
    };

    private int currentIndex = 0; // Track the current crop index

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops);

        // Initialize views
        imageViewFullCrop = findViewById(R.id.imageViewFullCrop);
        textViewCropName = findViewById(R.id.textViewCropName);
        buttonPreviousCrop = findViewById(R.id.buttonPreviousCrop);
        buttonNextCrop = findViewById(R.id.buttonNextCrop);
        backFromCropsActivity = findViewById(R.id.backFromCropsActivity); // Back button

        // Load the first crop
        loadCrop(currentIndex);

        // Set button listeners
        buttonPreviousCrop.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                loadCrop(currentIndex);
            }
        });

        buttonNextCrop.setOnClickListener(v -> {
            if (currentIndex < cropImages.length - 1) {
                currentIndex++;
                loadCrop(currentIndex);
            }
        });

        // Back button listener
        backFromCropsActivity.setOnClickListener(v -> finish());  // Close current activity and go back
    }

    private void loadCrop(int index) {
        imageViewFullCrop.setImageResource(cropImages[index]);
        textViewCropName.setText(cropNames[index]);
        playSound(cropSounds[index]);
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
