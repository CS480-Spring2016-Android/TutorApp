package com.zaidi.cs480.spring.app.tutortabby;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * LoginActivity Activity to log the user in.
 * Created by MAGarcia on 4/19/2016.
 */
public class LoginActivity extends AppCompatActivity {
  private EditText usernameLoginText;
  private EditText passwordLoginText;
  private ProgressBar loadBar;
  private Button loginButton;
  private View background;

  @Override
  public void onCreate(Bundle os) {
    super.onCreate(os);
    setContentView(R.layout.activity_login);

    usernameLoginText = (EditText)findViewById(R.id.userNameInput);
    passwordLoginText = (EditText)findViewById(R.id.pwUserInput);
    loginButton = (Button)findViewById(R.id.login_button);
    background = findViewById(R.id.login_layout);
    loadBar = (ProgressBar)findViewById(R.id.load_progress);
    // Animate the background
    animateBackground();

    // Activate User Interaction.
    setupUsernameText();
    setupPasswordText();
    setupLoginButton();

  }

  @Override
  protected void onPause() {
    super.onPause();


  }

  @Override
  protected void onStop() {
    super.onStop();


  }

  /**
   * Login Check and validation. This function links up to the query to search for the user
   * name and password in the data base.
   */
  // Perform Checking and login parameters.
  protected void performLogin() {
    if (usernameLoginText.length() <= 0) {
      Toast.makeText(LoginActivity.this, "Please enter a Username.", Toast.LENGTH_SHORT).show();
    } else {
      // Do the check to see if username and password match...
      // This is justa place holder lulz...
      startLoading();

      if (isLoggedIn(usernameLoginText.getText().toString(), passwordLoginText.getText().toString())) {
        // Move on to the user's account home screen or something...
        Toast.makeText(LoginActivity.this, "Ye boi, we did it!!", Toast.LENGTH_SHORT).show();
        login(null);
      } else {
        // Display to the user that s/he has not logged in successfully.
        Toast.makeText(LoginActivity.this
                     , "Username and Password do not match."
                     , Toast.LENGTH_SHORT).show();
        stopLoading();
      }
    }
  }

  protected void login(View view){
    Intent intent = new Intent(this, Profile.class);
    finish();
    startActivity(intent);
  }

  // Start the progress circle login load.
  private void startLoading() {
    loginButton.setVisibility(View.GONE);
    loadBar.setVisibility(View.VISIBLE);
  }

  // Stop the progress circle login load.
  private void stopLoading() {
    loginButton.setVisibility(View.VISIBLE);
    loadBar.setVisibility(View.GONE);
  }

  /**
   * Check to see if the user had successfully login in. Data base from our Database team is needed
   * in order to check for user info.
   * @return True if the user has logged in. False otherwise.
   */
  private boolean isLoggedIn(String username, String password) {
    // for now...
    return true;
  }


  /**
   * Background animation.
   */
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

  /**
   * Username text setup.
   */
  private void setupUsernameText() {
    usernameLoginText.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event != null) && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
          passwordLoginText.requestFocus();
          InputMethodManager imm = (InputMethodManager)getSystemService(Context
                  .INPUT_METHOD_SERVICE);
          imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
        return true;
      }
    });
  }

  /**
   * Password Text setup.
   */
  private void setupPasswordText() {
    passwordLoginText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                || (actionId == EditorInfo.IME_ACTION_DONE)) {
          performLogin();
        }
        return false;
      }
    });
  }

  /**
   * login button setup.
   */
  private void setupLoginButton() {
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        performLogin();
      }
    });
  }
}
