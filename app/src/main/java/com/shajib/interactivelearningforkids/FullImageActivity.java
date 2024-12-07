package com.shajib.interactivelearningforkids;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FullImageActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private int[] mAlphabetImages;
    private int[] mAlphabetAudios;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        // Retrieve data passed from AlphabetsActivity
        mAlphabetImages = getIntent().getIntArrayExtra("imageArray");
        mAlphabetAudios = getIntent().getIntArrayExtra("audioArray");
        mCurrentPosition = getIntent().getIntExtra("currentPosition", 0);

        // Initialize UI components
        ImageView imageView = findViewById(R.id.imageViewFull);
        Button buttonPrevious = findViewById(R.id.buttonPrevious);
        Button buttonNext = findViewById(R.id.buttonNext);
        ImageView buttonBack = findViewById(R.id.backFromFullImage);

        // Display the initial image and play its audio
        updateContent(imageView);

        // Back button functionality
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                finish();
            }
        });

        // Previous button functionality
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPosition > 0) {
                    mCurrentPosition--;
                    updateContent(imageView);
                }
            }
        });

        // Next button functionality
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPosition < mAlphabetImages.length - 1) {
                    mCurrentPosition++;
                    updateContent(imageView);
                }
            }
        });
    }

    // Method to update the displayed image and play the corresponding audio
    private void updateContent(ImageView imageView) {
        // Update the ImageView with the current image
        imageView.setImageResource(mAlphabetImages[mCurrentPosition]);

        // Stop any currently playing audio
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }

        // Play the corresponding audio
        mMediaPlayer = MediaPlayer.create(this, mAlphabetAudios[mCurrentPosition]);
        mMediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
