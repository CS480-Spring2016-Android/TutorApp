package com.zaidi.cs480.spring.app.tutortabby.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zaidi.cs480.spring.app.tutortabby.MusicService;
import com.zaidi.cs480.spring.app.tutortabby.R;


/**
 * About Us Activity intended to upload the information about this project, as well as the names of
 * each contributor.
 * Created by MAGarcia on 6/7/2016.
 */
public class AboutUsActivity extends Activity {
  /**
   * Provided that the intent is constructed for the Music Service.
   */
  private Intent intent;

  /**
   * Creates the Activity, with Music Service enabled automatically.
   * @param savedInstanceState bundled data is null.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_about_us);

    intent = new Intent(this, MusicService.class);
    intent.setAction("com.example.action.PLAY");
    startService(intent);
  }

  /**
   * Destroys the Activity, along with the Music Service.
   */
  @Override
  public void onDestroy() {
    super.onDestroy();

    stopService(intent);
  }
}
