package com.zaidi.cs480.spring.app.tutortabby;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Profile extends AppCompatActivity {

    private View background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        background = findViewById(R.id.profile_layout);
//        animateBackground();
    }

    private void animateBackground() {
        int colorFrom = ContextCompat.getColor(this, R.color.colorLoginBackground);
        int colorTo = ContextCompat.getColor(this, R.color.colorPrimary);

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
}
