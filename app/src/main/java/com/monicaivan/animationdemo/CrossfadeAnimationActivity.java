package com.monicaivan.animationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class CrossfadeAnimationActivity extends AppCompatActivity {

    private ImageView background;
    private ImageView foreground;
    private int longAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossfade_animation);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        longAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);

        foreground = findViewById(R.id.cross_image_1);
        background = findViewById(R.id.cross_image_2);

        background.setVisibility(View.GONE);
    }

    public void crossfade(View v){
        background.setAlpha(0f);
        background.setVisibility(View.VISIBLE);

        background.animate()
                .alpha(1f)
                .setDuration(longAnimationDuration)
                .setListener(null);
        v.setVisibility(View.GONE);
        foreground.animate()
                .alpha(0f)
                .setDuration(longAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        foreground.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
