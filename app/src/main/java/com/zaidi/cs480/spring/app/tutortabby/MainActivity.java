package com.zaidi.cs480.spring.app.tutortabby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // Set up any loading instructions to the back end if need be.
    // Anything the app needs to load up before starting.

    // Start up the LoginActivity screen.
    Intent loginScreen = new Intent(MainActivity.this, LoginActivity.class);
    startActivity(loginScreen);
    // Destroy the MainActivity StartUp.
    finish();
  }
}
