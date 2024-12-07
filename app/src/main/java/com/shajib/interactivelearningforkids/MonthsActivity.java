package com.shajib.interactivelearningforkids;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MonthsActivity extends AppCompatActivity {

    private static final int[] monthAudioIds = {
            R.raw.january, R.raw.february, R.raw.march, R.raw.april, R.raw.may,
            R.raw.june, R.raw.july, R.raw.august, R.raw.september, R.raw.october,
            R.raw.november, R.raw.december
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_months);

        // Find all LinearLayouts
        LinearLayout[] monthLayouts = new LinearLayout[12];
        for (int i = 0; i < 12; i++) {
            int layoutId = getResources().getIdentifier(getMonthLayoutName(i), "id", getPackageName());
            monthLayouts[i] = findViewById(layoutId);
        }

        // Set OnClickListener for each month Layout
        for (int i = 0; i < 12; i++) {
            final int finalI = i;
            monthLayouts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Play audio for the clicked month
                    playAudio(monthAudioIds[finalI]);
                }
            });
        }

        // Set up the back button
        ImageView backButton = findViewById(R.id.backFromMonthsActivity);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and return to the previous one
                onBackPressed();
            }
        });
    }

    // Method to play audio
    private void playAudio(int audioResId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, audioResId);
        mediaPlayer.start();
    }

    // Helper method to get the name of month layout based on index
    private String getMonthLayoutName(int index) {
        String[] monthNames = {"january_layout", "february_layout", "march_layout", "april_layout", "may_layout",
                "june_layout", "july_layout", "august_layout", "september_layout", "october_layout",
                "november_layout", "december_layout"};
        return monthNames[index];
    }
}
