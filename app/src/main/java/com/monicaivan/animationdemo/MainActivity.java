package com.monicaivan.animationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button drawableAnimationButton;
    Button springAnimationButton;
    Button flingAnimationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawableAnimationButton = findViewById(R.id.drawable_anim_button);
        springAnimationButton = findViewById(R.id.spring_anim_button);
        flingAnimationButton = findViewById(R.id.fling_anim_button);

        drawableAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DrawableAnimationActivity.class));
            }
        });
        springAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SpringAnimationActivity.class));
            }
        });
        flingAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FlingAnimationActivity.class));
            }
        });
    }
}
