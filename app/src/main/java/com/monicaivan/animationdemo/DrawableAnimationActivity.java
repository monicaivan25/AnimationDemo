package com.monicaivan.animationdemo;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DrawableAnimationActivity extends AppCompatActivity {

    AnimationDrawable animationDrawable;
    boolean started = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_animation);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        ImageView imageView = findViewById(R.id.imageView);
        imageView.setBackgroundResource(R.drawable.my_custom_animation);
        animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!started){
                    animationDrawable.start();
                    started = true;
                } else {
                    animationDrawable.stop();
                    started = false;
                }
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
