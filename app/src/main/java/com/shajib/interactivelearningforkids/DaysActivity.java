package com.shajib.interactivelearningforkids;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class DaysActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);

        // Set click listeners for each button
        setupButton(R.id.button_saturday, R.raw.saturday);
        setupButton(R.id.button_sunday, R.raw.sunday);
        setupButton(R.id.button_monday, R.raw.monday);
        setupButton(R.id.button_tuesday, R.raw.tuesday);
        setupButton(R.id.button_wednesday, R.raw.wednesday);
        setupButton(R.id.button_thursday, R.raw.thursday);
        setupButton(R.id.button_friday, R.raw.friday);

        // Set up the back button
        ImageView backButton = findViewById(R.id.backFromDaysActivity);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and return to the previous one
                onBackPressed();
            }
        });
    }

    private void setupButton(int buttonId, final int soundResourceId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Release any previous MediaPlayer instance
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }

                // Create a new MediaPlayer instance and start it
                mediaPlayer = MediaPlayer.create(DaysActivity.this, soundResourceId);
                mediaPlayer.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
