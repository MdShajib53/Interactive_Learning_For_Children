package com.shajib.interactivelearningforkids;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz2Activity extends AppCompatActivity {
    private final List<Pair> questionData = new ArrayList<>();
    private final boolean[] correctAnswersStatus = new boolean[10]; // Boolean array to track correctness
    private MediaPlayer mediaPlayer;
    private int totalMarks = 0;

    private CountDownTimer countDownTimer;
    private TextView quizTimer;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        // Initialize data
        initQuestionData();

        // Shuffle questions
        Collections.shuffle(questionData);

        // Set up questions
        setupQuestions();

        // Set up the timer TextView
        quizTimer = findViewById(R.id.quizTimer);

        // Submit button logic
        submitButton = findViewById(R.id.quizsubmit);
        submitButton.setOnClickListener(v -> {
            // Stop the countdown timer when submit is clicked
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            // Calculate and show results
            showResults();
        });

        // Set up the back button
        ImageView backButton = findViewById(R.id.backFromQuizActivity);
        backButton.setOnClickListener(v -> {
            // Stop the countdown timer when back is clicked
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            finish();  // Finish current activity to go back
        });

        // Start the timer with 5 minutes
        startQuizTimer();
    }

    private void initQuestionData() {
        // Alphabet pairs
        questionData.add(new Pair(R.drawable.a, R.raw.a, "a"));
        questionData.add(new Pair(R.drawable.b, R.raw.b, "b"));
        questionData.add(new Pair(R.drawable.c, R.raw.c, "c"));
        questionData.add(new Pair(R.drawable.d, R.raw.d, "d"));
        questionData.add(new Pair(R.drawable.e, R.raw.e, "e"));
        questionData.add(new Pair(R.drawable.f, R.raw.f, "f"));
        questionData.add(new Pair(R.drawable.g, R.raw.g, "g"));
        questionData.add(new Pair(R.drawable.h, R.raw.h, "h"));
        questionData.add(new Pair(R.drawable.i, R.raw.i, "i"));
        questionData.add(new Pair(R.drawable.j, R.raw.j, "j"));
        questionData.add(new Pair(R.drawable.k, R.raw.k, "k"));
        questionData.add(new Pair(R.drawable.l, R.raw.l, "l"));
        questionData.add(new Pair(R.drawable.m, R.raw.m, "m"));
        questionData.add(new Pair(R.drawable.n, R.raw.n, "n"));
        questionData.add(new Pair(R.drawable.o, R.raw.o, "o"));
        questionData.add(new Pair(R.drawable.p, R.raw.p, "p"));
        questionData.add(new Pair(R.drawable.q, R.raw.q, "q"));
        questionData.add(new Pair(R.drawable.r, R.raw.r, "r"));
        questionData.add(new Pair(R.drawable.s, R.raw.s, "s"));
        questionData.add(new Pair(R.drawable.t, R.raw.t, "t"));
        questionData.add(new Pair(R.drawable.u, R.raw.u, "u"));
        questionData.add(new Pair(R.drawable.v, R.raw.v, "v"));
        questionData.add(new Pair(R.drawable.w, R.raw.w, "w"));
        questionData.add(new Pair(R.drawable.x, R.raw.x, "x"));
        questionData.add(new Pair(R.drawable.y, R.raw.y, "y"));
        questionData.add(new Pair(R.drawable.z, R.raw.z, "z"));

        // Additional named pairs
        questionData.add(new Pair(R.drawable.ant, R.raw.ant, "ant"));
        questionData.add(new Pair(R.drawable.apple, R.raw.apple, "apple"));
        questionData.add(new Pair(R.drawable.arm, R.raw.arm, "arm"));
        questionData.add(new Pair(R.drawable.bear, R.raw.bear, "bear"));
        questionData.add(new Pair(R.drawable.black_plum, R.raw.black_plum, "black_plum"));
        questionData.add(new Pair(R.drawable.burflower, R.raw.burflower, "burflower"));
        questionData.add(new Pair(R.drawable.burmese_grape, R.raw.burmese_grape, "burmese_grape"));
        questionData.add(new Pair(R.drawable.camel, R.raw.camel, "camel"));
        questionData.add(new Pair(R.drawable.cat, R.raw.cat, "cat"));
        questionData.add(new Pair(R.drawable.chickpea, R.raw.chickpea, "chickpea"));
        questionData.add(new Pair(R.drawable.coconut, R.raw.coconut, "coconut"));
        questionData.add(new Pair(R.drawable.cow, R.raw.cow, "cow"));
        questionData.add(new Pair(R.drawable.crow, R.raw.crow, "crow"));
        questionData.add(new Pair(R.drawable.cuckoo, R.raw.cuckoo, "cuckoo"));
        questionData.add(new Pair(R.drawable.deer, R.raw.deer, "deer"));
        questionData.add(new Pair(R.drawable.dog, R.raw.dog, "dog"));
        questionData.add(new Pair(R.drawable.eagle, R.raw.eagle, "eagle"));
        questionData.add(new Pair(R.drawable.ears, R.raw.ears, "ears"));
        questionData.add(new Pair(R.drawable.elephant, R.raw.elephant, "elephant"));
        questionData.add(new Pair(R.drawable.eye, R.raw.eye, "eye"));
        questionData.add(new Pair(R.drawable.fingers, R.raw.fingers, "fingers"));
        questionData.add(new Pair(R.drawable.flame_tree, R.raw.flame_tree, "flame_tree"));
        questionData.add(new Pair(R.drawable.fox, R.raw.fox, "fox"));
        questionData.add(new Pair(R.drawable.grapes, R.raw.grapes, "grapes"));
        questionData.add(new Pair(R.drawable.groundnut, R.raw.groundnut, "groundnut"));
        questionData.add(new Pair(R.drawable.hair, R.raw.hair, "hair"));
        questionData.add(new Pair(R.drawable.hand, R.raw.hand, "hand"));
        questionData.add(new Pair(R.drawable.hibiscus, R.raw.hibiscus, "hibiscus"));
        questionData.add(new Pair(R.drawable.hummingbird, R.raw.hummingbird, "hummingbird"));
        questionData.add(new Pair(R.drawable.jackfruit, R.raw.jackfruit, "jackfruit"));
        questionData.add(new Pair(R.drawable.jujube, R.raw.jujube, "jujube"));
        questionData.add(new Pair(R.drawable.jute, R.raw.jute, "jute"));


        // Additional named pairs
        questionData.add(new Pair(R.drawable.kingfisher, R.raw.kingfisher, "kingfisher"));
        questionData.add(new Pair(R.drawable.legs, R.raw.legs, "legs"));
        questionData.add(new Pair(R.drawable.lentil, R.raw.lentil, "lentil"));
        questionData.add(new Pair(R.drawable.lips, R.raw.lips, "lips"));
        questionData.add(new Pair(R.drawable.lychee, R.raw.lychee, "lychee"));
        questionData.add(new Pair(R.drawable.magpie_robin, R.raw.magpie_robin, "magpie_robin"));
        questionData.add(new Pair(R.drawable.maize, R.raw.maize, "maize"));
        questionData.add(new Pair(R.drawable.mango, R.raw.mango, "mango"));
        questionData.add(new Pair(R.drawable.marigold, R.raw.marigold, "marigold"));
        questionData.add(new Pair(R.drawable.mustard, R.raw.mustard, "mustard"));
        questionData.add(new Pair(R.drawable.night_jasmine, R.raw.night_jasmine, "night_jasmine"));
        questionData.add(new Pair(R.drawable.nose, R.raw.nose, "nose"));
        questionData.add(new Pair(R.drawable.orange, R.raw.orange, "orange"));
        questionData.add(new Pair(R.drawable.owl, R.raw.owl, "owl"));
        questionData.add(new Pair(R.drawable.parrot, R.raw.parrot, "parrot"));
        questionData.add(new Pair(R.drawable.pigeon, R.raw.pigeon, "pigeon"));
        questionData.add(new Pair(R.drawable.potato, R.raw.potato, "potato"));
        questionData.add(new Pair(R.drawable.rice, R.raw.rice, "rice"));
        questionData.add(new Pair(R.drawable.rose, R.raw.rose, "rose"));
        questionData.add(new Pair(R.drawable.sesame, R.raw.sesame, "sesame"));
        questionData.add(new Pair(R.drawable.sparrow, R.raw.sparrow, "sparrow"));
        questionData.add(new Pair(R.drawable.sunflower, R.raw.sunflower, "sunflower"));
        questionData.add(new Pair(R.drawable.teeth, R.raw.teeth, "teeth"));
        questionData.add(new Pair(R.drawable.tiger, R.raw.tiger, "tiger"));
        questionData.add(new Pair(R.drawable.tuberose, R.raw.tuberose, "tuberose"));
        questionData.add(new Pair(R.drawable.water_lily, R.raw.water_lily, "water_lilly"));
        questionData.add(new Pair(R.drawable.wheat, R.raw.wheat, "wheat"));

    }



    private void setupQuestions() {
        for (int i = 0; i < 10; i++) {
            // Get image and radio group IDs dynamically
            int imageId = getResources().getIdentifier("q" + (i + 1) + "Image", "id", getPackageName());
            int optionsId = getResources().getIdentifier("q" + (i + 1) + "Options", "id", getPackageName());

            ImageView imageView = findViewById(imageId);
            RadioGroup radioGroup = findViewById(optionsId);

            if (imageView == null || radioGroup == null) {
                continue; // Skip if any view is missing
            }

            // Get the question data
            final Pair question = questionData.get(i);  // Make this final

            // Set the image
            imageView.setImageResource(question.imageResId);

            // Create a list of 4 audio options (1 correct + 3 random wrong answers)
            List<Integer> options = new ArrayList<>();
            options.add(question.soundResId);

            // Add 3 wrong answers (randomly chosen)
            for (int j = 0; j < 3; j++) {
                int wrongAnswer = getRandomWrongAnswer(question.soundResId);
                options.add(wrongAnswer);
            }

            // Shuffle the options
            Collections.shuffle(options);

            // Assign the audio options to radio buttons
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(j);
                radioButton.setText(String.valueOf((char) ('a' + j)));  // Change option text to 'a', 'b', 'c', 'd'
                final int soundResId = options.get(j);  // Make this final

                // Assign click listener for each option
                final int index = i; // Track the question index
                radioButton.setOnClickListener(view -> handleOptionClick(index, soundResId, question.soundResId));
            }
        }
    }

    private int getRandomWrongAnswer(int correctAnswerId) {
        int wrongAnswer;
        do {
            // Select a random wrong answer (not the same as the correct one)
            wrongAnswer = questionData.get((int) (Math.random() * questionData.size())).soundResId;
        } while (wrongAnswer == correctAnswerId);
        return wrongAnswer;
    }

    private void handleOptionClick(int index, int selectedSoundResId, int correctSoundResId) {
        stopPlaying();

        // Play the selected sound
        mediaPlayer = MediaPlayer.create(this, selectedSoundResId);
        mediaPlayer.start();

        // Update correctness status for this question
        correctAnswersStatus[index] = (selectedSoundResId == correctSoundResId);
    }

    private void showResults() {
        stopPlaying();

        // Calculate total marks based on correctness status
        totalMarks = 0;
        for (boolean isCorrect : correctAnswersStatus) {
            if (isCorrect) {
                totalMarks++;
            }
        }

        // Show results in a dialog box
        new AlertDialog.Builder(this)
                .setTitle("Quiz Results")
                .setMessage("You scored " + totalMarks + " out of " + correctAnswersStatus.length + "!")
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();  // Dismiss the dialog
                    navigateToMainActivity();  // Navigate to MainActivity after clicking "OK"
                })
                .show();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(Quiz2Activity.this, MainActivity.class);
        startActivity(intent);  // Start the MainActivity
        finish();  // Close the current QuizActivity
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void startQuizTimer() {
        // 3 minutes = 180,000 milliseconds
        countDownTimer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Calculate minutes and seconds
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                // Update timer TextView
                quizTimer.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                // Automatically submit when time is up
                submitButton.performClick();
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlaying();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    // Pair class for storing question data (image, sound, and correct answer)
    static class Pair {
        int imageResId;
        int soundResId;
        String correctAnswer;

        Pair(int imageResId, int soundResId, String correctAnswer) {
            this.imageResId = imageResId;
            this.soundResId = soundResId;
            this.correctAnswer = correctAnswer;
        }
    }
}
