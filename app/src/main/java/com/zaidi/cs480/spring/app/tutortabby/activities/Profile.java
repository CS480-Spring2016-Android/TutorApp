package com.zaidi.cs480.spring.app.tutortabby.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zaidi.cs480.spring.app.tutortabby.R;

public class Profile extends AppCompatActivity {

    private View background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Added feature to change animation transition to changeable colors instead.
        // You can now specify which colors you want to transition to for your background.
        //background = findViewById(R.id.profile_layout);
        //animateBackground(R.color.colorPrimary, R.color.colorLoginBackgroundDark);
    }

    private void animateBackground(int idFrom, int idTo) {
        int colorFrom = ContextCompat.getColor(this, idFrom);
        int colorTo = ContextCompat.getColor(this, idTo);

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(5000);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                background.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
