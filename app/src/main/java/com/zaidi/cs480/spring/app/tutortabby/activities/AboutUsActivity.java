package com.zaidi.cs480.spring.app.tutortabby.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zaidi.cs480.spring.app.tutortabby.MusicService;
import com.zaidi.cs480.spring.app.tutortabby.R;


/**
 * Created by MAGarcia on 6/7/2016.
 */
public class AboutUsActivity extends Activity {
  private Intent intent;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_about_us);

    intent = new Intent(this, MusicService.class);
    intent.setAction("com.example.action.PLAY");
    startService(intent);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    stopService(intent);
  }
}
