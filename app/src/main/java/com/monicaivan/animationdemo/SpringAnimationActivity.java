package com.monicaivan.animationdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

public class SpringAnimationActivity extends AppCompatActivity {

    private static final String TAG = "SpringAnimationActivity";
    private final Float STIFFNESS = SpringForce.STIFFNESS_LOW;
    private final Float DAMPING_RATIO = SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
    private SpringAnimation xAnimation;
    private SpringAnimation yAnimation;
    float dX = 0f;
    float dY = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_animation);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final View imageView = findViewById(R.id.bug);

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                xAnimation = createSpringAnimation(imageView, SpringAnimation.X, imageView.getX(), STIFFNESS, DAMPING_RATIO);
                yAnimation = createSpringAnimation(imageView, SpringAnimation.Y, imageView.getY(), STIFFNESS, DAMPING_RATIO);
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();

                        xAnimation.cancel();
                        yAnimation.cancel();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        imageView.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;
                    case MotionEvent.ACTION_UP:
                        xAnimation.start();
                        yAnimation.start();
                        break;
                }
                return true;
            }
        });

    }



    private SpringAnimation createSpringAnimation(View view,
                                                  DynamicAnimation.ViewProperty property,
                                                  Float finalPosition, Float stiffness, Float dampingRatio){
        SpringForce springForce = new SpringForce(finalPosition);
        springForce.setDampingRatio(dampingRatio);
        springForce.setStiffness(stiffness);

        SpringAnimation springAnimation = new SpringAnimation(view, property);
        springAnimation.setSpring(springForce);

        return springAnimation;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
