package com.shajib.interactivelearningforkids;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FlowersActivity extends AppCompatActivity {

    private ImageView imageViewFullFlower, backFromFlowersActivity;
    private TextView textViewFlowerName;
    private Button buttonPreviousFlower, buttonNextFlower;
    private MediaPlayer mediaPlayer;

    // Flower images
    private int[] flowerImages = {
            R.drawable.rose,
            R.drawable.marigold,
            R.drawable.hibiscus,
            R.drawable.water_lily,
            R.drawable.sunflower,
            R.drawable.jasmine,
            R.drawable.tuberose,
            R.drawable.night_jasmine,
            R.drawable.burflower,
            R.drawable.flame_tree
    };

    // Flower sounds (optional, replace with actual files if available)
    private int[] flowerSounds = {
            R.raw.rose,
            R.raw.marigold,
            R.raw.hibiscus,
            R.raw.water_lily,
            R.raw.sunflower,
            R.raw.jasmine,
            R.raw.tuberose,
            R.raw.night_jasmine,
            R.raw.burflower,
            R.raw.flame_tree
    };

    // Flower names
    private String[] flowerNames = {
            "Rose",
            "Marigold",
            "Hibiscus",
            "Water Lily",
            "Sunflower",
            "Jasmine",
            "Tuberose",
            "Night Jasmine",
            "Burflower",
            "Flame Tree"
    };

    private int currentIndex = 0; // Track the current flower index

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowers);

        // Initialize views
        imageViewFullFlower = findViewById(R.id.imageViewFullFlower);
        textViewFlowerName = findViewById(R.id.textViewFlowerName);
        buttonPreviousFlower = findViewById(R.id.buttonPreviousFlower);
        buttonNextFlower = findViewById(R.id.buttonNextFlower);
        backFromFlowersActivity = findViewById(R.id.backFromFlowersActivity); // Back button

        // Load the first flower
        loadFlower(currentIndex);

        // Set button listeners
        buttonPreviousFlower.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                loadFlower(currentIndex);
            }
        });

        buttonNextFlower.setOnClickListener(v -> {
            if (currentIndex < flowerImages.length - 1) {
                currentIndex++;
                loadFlower(currentIndex);
            }
        });

        // Back button listener
        backFromFlowersActivity.setOnClickListener(v -> finish());  // Close current activity and go back
    }

    private void loadFlower(int index) {
        imageViewFullFlower.setImageResource(flowerImages[index]);
        textViewFlowerName.setText(flowerNames[index]);
        playSound(flowerSounds[index]);
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
