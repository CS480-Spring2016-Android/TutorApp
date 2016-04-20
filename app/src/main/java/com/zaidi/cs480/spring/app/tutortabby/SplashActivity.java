package com.zaidi.cs480.spring.app.tutortabby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Starts up the Tutor app with a splash screen.
 * Created by MAGarcia on 4/19/2016.
 */
public class SplashActivity extends AppCompatActivity {
  @Override
  public void onCreate(Bundle os) {
    super.onCreate(os);

    Intent mainActivity = new Intent(this, MainActivity.class);
    startActivity(mainActivity);
    finish();
  }
}
