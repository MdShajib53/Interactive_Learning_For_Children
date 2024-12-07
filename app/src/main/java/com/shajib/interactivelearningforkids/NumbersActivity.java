package com.shajib.interactivelearningforkids;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        // Setup sound buttons
        setupButton(R.id.button_one, R.raw.one);
        setupButton(R.id.button_two, R.raw.two);
        setupButton(R.id.button_three, R.raw.three);
        setupButton(R.id.button_four, R.raw.four);
        setupButton(R.id.button_five, R.raw.five);
        setupButton(R.id.button_six, R.raw.six);
        setupButton(R.id.button_seven, R.raw.seven);
        setupButton(R.id.button_eight, R.raw.eight);
        setupButton(R.id.button_nine, R.raw.nine);
        setupButton(R.id.button_ten, R.raw.ten);

        // Back button functionality
        ImageView backButton = findViewById(R.id.backFromNumbersActivity);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the previous activity (MainActivity in this case)
                onBackPressed(); // This will take you back to the previous activity
            }
        });
    }

    private void setupButton(int buttonId, final int soundResource) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(soundResource);
            }
        });
    }

    private void playSound(int soundResource) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, soundResource);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });
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
