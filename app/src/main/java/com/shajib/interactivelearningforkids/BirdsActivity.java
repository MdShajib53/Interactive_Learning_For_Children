package com.shajib.interactivelearningforkids;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BirdsActivity extends AppCompatActivity {

    private ImageView imageViewFull;
    private TextView textViewBirdName;
    private Button buttonPrevious, buttonNext;
    private MediaPlayer mediaPlayer;
    private ImageView backFromBirdsActivity; // Back button

    private int[] birdImages = {
            R.drawable.magpie_robin,
            R.drawable.parrot,
            R.drawable.pigeon,
            R.drawable.kingfisher,
            R.drawable.hummingbird,
            R.drawable.owl,
            R.drawable.sparrow,
            R.drawable.crow,
            R.drawable.cuckoo,
            R.drawable.eagle
    };

    private int[] birdSounds = {
            R.raw.magpie_robin,
            R.raw.parrot,
            R.raw.pigeon,
            R.raw.kingfisher,
            R.raw.hummingbird,
            R.raw.owl,
            R.raw.sparrow,
            R.raw.crow,
            R.raw.cuckoo,
            R.raw.eagle
    };

    private String[] birdNames = {
            "Oriental Magpie-Robin",
            "Parrot",
            "Pigeon",
            "Kingfisher",
            "Hummingbird",
            "Owl",
            "Sparrow",
            "Crow",
            "Cuckoo",
            "Eagle"
    };

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birds);

        imageViewFull = findViewById(R.id.imageViewFull);
        textViewBirdName = findViewById(R.id.textViewBirdName);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);
        backFromBirdsActivity = findViewById(R.id.backFromBirdsActivity); // Initialize back button

        loadBird(currentIndex);

        buttonPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                loadBird(currentIndex);
            }
        });

        buttonNext.setOnClickListener(v -> {
            if (currentIndex < birdImages.length - 1) {
                currentIndex++;
                loadBird(currentIndex);
            }
        });

        // Set the back button click listener
        backFromBirdsActivity.setOnClickListener(v -> {
            // Finish the activity and go back to the previous one
            finish();
        });
    }

    private void loadBird(int index) {
        imageViewFull.setImageResource(birdImages[index]);
        textViewBirdName.setText(birdNames[index]);
        playSound(birdSounds[index]);
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
