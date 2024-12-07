package com.shajib.interactivelearningforkids;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FruitsActivity extends AppCompatActivity {

    private ImageView imageViewFull;
    private TextView textViewFruitName;
    private Button buttonPrevious, buttonNext;
    private MediaPlayer mediaPlayer;
    private ImageView backFromFruitsActivity; // Back button

    // Fruit images
    private int[] fruitImages = {
            R.drawable.mango,
            R.drawable.black_plum,
            R.drawable.jackfruit,
            R.drawable.lychee,
            R.drawable.apple,
            R.drawable.orange,
            R.drawable.grapes,
            R.drawable.jujube,
            R.drawable.burmese_grape,
            R.drawable.coconut
    };

    // Fruit sounds (optional, replace with actual files if you have them)
    private int[] fruitSounds = {
            R.raw.mango,
            R.raw.black_plum,
            R.raw.jackfruit,
            R.raw.lychee,
            R.raw.apple,
            R.raw.orange,
            R.raw.grapes,
            R.raw.jujube,
            R.raw.burmese_grape,
            R.raw.coconut
    };

    // Fruit names
    private String[] fruitNames = {
            "Mango",
            "Black Plum",
            "Jackfruit",
            "Lychee",
            "Apple",
            "Orange",
            "Grapes",
            "Jujube",
            "Burmese Grape",
            "Coconut"
    };

    private int currentIndex = 0; // Track current fruit index

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits);

        // Initialize views
        imageViewFull = findViewById(R.id.imageViewFull);
        textViewFruitName = findViewById(R.id.textViewFruitName);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);
        backFromFruitsActivity = findViewById(R.id.backFromFruitsActivity); // Initialize back button

        // Load the first fruit
        loadFruit(currentIndex);

        // Set button listeners
        buttonPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                loadFruit(currentIndex);
            }
        });

        buttonNext.setOnClickListener(v -> {
            if (currentIndex < fruitImages.length - 1) {
                currentIndex++;
                loadFruit(currentIndex);
            }
        });

        // Set the back button click listener
        backFromFruitsActivity.setOnClickListener(v -> {
            // Finish the activity and go back to the previous one
            finish();
        });
    }

    private void loadFruit(int index) {
        imageViewFull.setImageResource(fruitImages[index]);
        textViewFruitName.setText(fruitNames[index]);
        playSound(fruitSounds[index]);
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
