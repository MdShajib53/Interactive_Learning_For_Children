package com.shajib.interactivelearningforkids;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AlphabetsActivity extends AppCompatActivity {
    private int[] mAlphabetImages = {
            R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
            R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l,
            R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p,
            R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t,
            R.drawable.u, R.drawable.v, R.drawable.w, R.drawable.x,
            R.drawable.y, R.drawable.z
    };

    private int[] mAlphabetAudios = {
            R.raw.a, R.raw.b, R.raw.c, R.raw.d,
            R.raw.e, R.raw.f, R.raw.g, R.raw.h,
            R.raw.i, R.raw.j, R.raw.k, R.raw.l,
            R.raw.m, R.raw.n, R.raw.o, R.raw.p,
            R.raw.q, R.raw.r, R.raw.s, R.raw.t,
            R.raw.u, R.raw.v, R.raw.w, R.raw.x,
            R.raw.y, R.raw.z
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabets);

        // Initialize GridView and set the adapter
        GridView gridView = findViewById(R.id.Alphabets_gridView);
        AlphabetAdapter adapter = new AlphabetAdapter(this, mAlphabetImages);
        gridView.setAdapter(adapter);

        // Add the OnItemClickListener to handle clicks on GridView items
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Create an Intent to start FullImageActivity
                Intent intent = new Intent(AlphabetsActivity.this, FullImageActivity.class);

                // Pass the array of images and audios, and the clicked position
                intent.putExtra("imageArray", mAlphabetImages);
                intent.putExtra("audioArray", mAlphabetAudios);
                intent.putExtra("currentPosition", position);

                // Start FullImageActivity
                startActivity(intent);
            }
        });

        // Back button functionality
        ImageView backButton = findViewById(R.id.backFromAlphabetsActivity);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                onBackPressed();
            }
        });
    }
}
