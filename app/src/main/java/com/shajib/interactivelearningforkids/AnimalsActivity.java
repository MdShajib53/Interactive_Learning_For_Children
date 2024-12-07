package com.shajib.interactivelearningforkids;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AnimalsActivity extends AppCompatActivity {

    private ImageView imageViewFull;
    private TextView textViewAnimalName;
    private Button buttonPrevious, buttonNext;
    private MediaPlayer mediaPlayer;
    private ImageView backFromAnimalsActivity; // Back button

    private int[] animalImages = {
            R.drawable.tiger,
            R.drawable.deer,
            R.drawable.elephant,
            R.drawable.fox,
            R.drawable.cat,
            R.drawable.dog,
            R.drawable.cow,
            R.drawable.bear,
            R.drawable.ant,
            R.drawable.camel
    };

    private int[] animalSounds = {
            R.raw.tiger,
            R.raw.deer,
            R.raw.elephant,
            R.raw.fox,
            R.raw.cat,
            R.raw.dog,
            R.raw.cow,
            R.raw.bear,
            R.raw.ant,
            R.raw.camel
    };

    private String[] animalNames = {
            "Tiger",
            "Deer",
            "Elephant",
            "Fox",
            "Cat",
            "Dog",
            "Cow",
            "Bear",
            "Ant",
            "Camel"
    };

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);

        imageViewFull = findViewById(R.id.imageViewFull);
        textViewAnimalName = findViewById(R.id.textViewAnimalName);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);
        backFromAnimalsActivity = findViewById(R.id.backFromAnimalsActivity); // Initialize back button

        loadAnimal(currentIndex);

        buttonPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                loadAnimal(currentIndex);
            }
        });

        buttonNext.setOnClickListener(v -> {
            if (currentIndex < animalImages.length - 1) {
                currentIndex++;
                loadAnimal(currentIndex);
            }
        });

        // Set the back button click listener
        backFromAnimalsActivity.setOnClickListener(v -> {
            // Finish the activity and go back to the previous one
            finish();
        });
    }

    private void loadAnimal(int index) {
        imageViewFull.setImageResource(animalImages[index]);
        textViewAnimalName.setText(animalNames[index]);
        playSound(animalSounds[index]);
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
