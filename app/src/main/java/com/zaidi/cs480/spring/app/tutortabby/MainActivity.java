package com.zaidi.cs480.spring.app.tutortabby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  protected void login(View view){
    Intent intent = new Intent(this, Profile.class);
    finish();
    startActivity(intent);
  }
}
