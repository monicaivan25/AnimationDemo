package com.monicaivan.animationdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;

import com.q42.android.scrollingimageview.ScrollingImageView;

public class FlingAnimationActivity extends AppCompatActivity {

    private FlingAnimation animX;
    private FlingAnimation animY;

    private ImageView imageView;
    private Float downXValue;
    private Float downYValue;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fling_animation);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ScrollingImageView scrollingBackground = findViewById(R.id.cloud_background);
        scrollingBackground.stop();
        scrollingBackground.start();

        imageView = findViewById(R.id.cat_plane);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: {
                        downXValue = event.getX();
                        downYValue = event.getY();
                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        float currentX = event.getX();
                        float currentY = event.getY();
                        animX = createFlingAnimation(imageView, DynamicAnimation.X, currentX - downXValue, 0.5f);
                        animX.start();

                        animY = createFlingAnimation(imageView, DynamicAnimation.Y, currentY - downYValue, 0.5f);
                        animY.start();

                        break;
                    }
                }
                return true;
            }
        });
    }

    private FlingAnimation createFlingAnimation(View view,
                                                DynamicAnimation.ViewProperty property,
                                                Float velocity, Float friction) {
        FlingAnimation flingAnimation = new FlingAnimation(view, property);
        flingAnimation.setStartVelocity(velocity);
        flingAnimation.setFriction(friction);
        return flingAnimation;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
