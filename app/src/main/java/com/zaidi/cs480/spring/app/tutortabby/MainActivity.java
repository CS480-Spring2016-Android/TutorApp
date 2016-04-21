package com.zaidi.cs480.spring.app.tutortabby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Set value if there was a runtime error within the preload...
    boolean successfulLoad = true;
    setContentView(R.layout.activity_main);
    // Set up any loading instructions to the back end if need be.
    // Anything the app needs to load up before starting.

    // Start up the LoginActivity screen.
    Intent loginScreen = new Intent(MainActivity.this, LoginActivity.class);
    loginScreen.putExtra("programLoaded", successfulLoad);
    startActivity(loginScreen);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    // Destroy the MainActivity StartUp.
    finish();
  }
}
