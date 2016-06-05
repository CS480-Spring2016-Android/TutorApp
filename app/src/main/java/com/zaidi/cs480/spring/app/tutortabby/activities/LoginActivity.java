package com.zaidi.cs480.spring.app.tutortabby.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zaidi.cs480.spring.app.tutortabby.R;
import com.zaidi.cs480.spring.app.tutortabby.fragments.ForgotPasswordFragment;
import com.zaidi.cs480.spring.app.tutortabby.fragments.SignupFragment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

/**
 * LoginActivity Activity to log the user in.
 * Created by MAGarcia on 4/19/2016.
 */
public class LoginActivity extends FragmentActivity
        implements SignupFragment.OnFragmentInteractionListener
                 , ForgotPasswordFragment.OnForgotPasswordFragmentListener {
  private EditText usernameLoginText;
  private EditText passwordLoginText;
  private ProgressBar loadBar;
  private Button loginButton;
  private View background;
  private Bundle savedInstanceState;
  private static final String URL_LINK = "jdbc:mariadb://db.zer0-one.net/tutorWeb";
  protected Queue<String> signUp = new ArrayDeque<String>();

  @Override
  public void onCreate(Bundle os) {
    super.onCreate(os);
    setContentView(R.layout.activity_login);

    usernameLoginText = (EditText)findViewById(R.id.userNameInput);
    passwordLoginText = (EditText)findViewById(R.id.pwUserInput);
    loginButton = (Button)findViewById(R.id.login_button);
    background = findViewById(R.id.login_layout);
    loadBar = (ProgressBar)findViewById(R.id.load_progress);
    savedInstanceState = os;

    boolean successful = getIntent().getBooleanExtra("programLoaded", false);
//    if (successful) {
//      Toast.makeText(this, "Yep", Toast.LENGTH_SHORT).show();
//    } else {
//      Toast.makeText(this, "Nope", Toast.LENGTH_SHORT).show();
//    }
    // Animate the background
    animateBackground(R.color.colorLoginBackgroundDark, R.color.colorPrimary);

    // Activate User Interaction.
  //  setupUsernameText();
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
      disableActivity();

      ResultSet res;
      try {
        if ((res = isLoggedIn(usernameLoginText.getText().toString(), passwordLoginText.getText().toString())) != null && res.next()) {
          // Move on to the user's account home screen or something...
          //Test code for now to get to the user profile screen
          if(res.getInt("count") == 2) {
            Intent intent = new Intent(this, Profile.class);
            intent.putExtra("uid", res.getInt("id"));
            intent.putExtra("user", res.getString("name"));
            intent.putExtra("source", "tutor&student");
            intent.putExtra("auth", true);
            intent.putExtra("pass", res.getString("pass"));

            finish();
            startActivity(intent);
          }
          else {
            Intent intent = new Intent(this, Profile.class);
            intent.putExtra("uid", res.getInt("id"));
            intent.putExtra("user", res.getString("name"));
            intent.putExtra("source", res.getString("source"));
            intent.putExtra("auth", true);
            intent.putExtra("pass", res.getString("pass"));

            finish();
            startActivity(intent);
          }
          //end test code
        } else {
          // Display to the user that s/he has not logged in successfully.
          Toast.makeText(LoginActivity.this
                  , "Username or Password do not match."
                  , Toast.LENGTH_SHORT).show();
          stopLoading();
          enableActivity();
        }
      } catch (SQLException sqe) {
        System.out.println(sqe.getMessage());
      }
    }
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
  private ResultSet isLoggedIn(String username, String password) {
    DBLoginActivity act = new DBLoginActivity(this);
    String sql = "select t.id, t.name, t.source, t.pass, count(*) as count from ((select tutorID as id, tutorName as name, 'tutor' as source, tutorPassword as pass from tutor where tutorName = \"" + username + "\" and tutorPassword = \"" + password + "\") union (select studentID as id, studentName as name, 'student' as source, studentPassword as pass from student where studentName = \"" + username + "\" and studentPassword = \"" + password + "\")) t group by t.name";
    act.execute("exe", sql);
    ResultSet res = null;
    String txt = "";
    int value = -1;
    try {
      res = act.get();

//      if(res != null){
//        try {
//          if(res.next())
//            value = res.getInt("id");
//        }
//        catch (SQLException sqlE){
//          Toast.makeText(LoginActivity.this, sqlE.toString(), Toast.LENGTH_LONG).show();
//          value = -1;
//        }
//      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    // for now...
    return res;
  }


  /**
   * Background animation.
   */
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

  /**
   * This function should not be used unless really needed.
   * Small bug will cause login to focus password text.
   * Username text setup.
   */
  @Deprecated
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

  private void disableActivity() {
    loginButton.setVisibility(View.GONE);
    usernameLoginText.setEnabled(false);
    passwordLoginText.setEnabled(false);

    TextView signupText = (TextView) findViewById(R.id.signupText);
    signupText.setEnabled(false);
    TextView forgotPassword = (TextView)findViewById(R.id.forgotPasswordText);
    forgotPassword.setEnabled(false);
  }

  private void enableActivity() {
    loginButton.setVisibility(View.VISIBLE);
    usernameLoginText.setEnabled(true);
    passwordLoginText.setEnabled(true);

    TextView signupText = (TextView) findViewById(R.id.signupText);
    signupText.setEnabled(true);
    TextView forgotPassword = (TextView)findViewById(R.id.forgotPasswordText);
    forgotPassword.setEnabled(true);
  }

  /**
   *
   * @param v
   */
  public void onRunSignup(View v) {
    if (findViewById(R.id.login_layout) != null) {
      if (savedInstanceState != null) {
        return;
      }

      SignupFragment newFragment = new SignupFragment();

      newFragment.setArguments(getIntent().getExtras());

      FragmentTransaction fragTransit = getSupportFragmentManager().beginTransaction();
      fragTransit.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                                      android.R.anim.slide_in_left, android.R.anim.slide_out_right);
      fragTransit.replace(R.id.login_layout, newFragment);
      fragTransit.addToBackStack(null);

      disableActivity();

      fragTransit.commit();
    }
  }

  /**
   *
   * @param v
   */
  public void onForgotPassword(View v) {
    if (findViewById(R.id.login_layout) != null) {
      if (savedInstanceState != null) {
        return;
      }

      ForgotPasswordFragment newFragment = new ForgotPasswordFragment();

      newFragment.setArguments(getIntent().getExtras());

      FragmentTransaction fragTransit = getSupportFragmentManager().beginTransaction();
      fragTransit.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
              android.R.anim.slide_in_left, android.R.anim.slide_out_right);
      fragTransit.replace(R.id.login_layout, newFragment);
      fragTransit.addToBackStack(null);

      disableActivity();

      fragTransit.commit();
    }
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

  public void onDone(View view) {
    String firstName = ((EditText) findViewById(R.id.firstName)).getText().toString();
    String lastName = ((EditText) findViewById(R.id.lastName)).getText().toString();
    String name = firstName + " " + lastName;

    String emailText = ((EditText) findViewById(R.id.emailText)).getText().toString();
    String pass = ((EditText) findViewById(R.id.newPassW)).getText().toString();
    String role = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();


    DBLoginActivity act = new DBLoginActivity(this);
    String sql;
    String success = "Account Not Created";

    if(name.trim().compareTo("") != 0 && pass.trim().compareTo("") != 0 && emailText.trim().compareTo("") != 0) {
      if (role.compareTo("Tutor") == 0) {
        sql = "INSERT INTO tutor (tutorName, tutorPassword, tutorEmail) values(\"" + name + "\",\"" + pass + "\",\"" + emailText + "\")";
      } else {
        sql = "INSERT INTO student (studentName, studentPassword, studentEmail) values(\"" + name + "\",\"" + pass + "\",\"" + emailText + "\")";
      }
    }
    else
      sql = null;

    if(sql != null){
      act.execute("up", sql);
      try {
        act.get();
        success = "Account Created";
      }
      catch (Exception e){
        System.out.println(e.getMessage());
      }
    }

    Toast.makeText(getBaseContext(), success, Toast.LENGTH_SHORT).show();

    getSupportFragmentManager().popBackStack();
    enableActivity();
  }

  public void onSend(View View) {
    getSupportFragmentManager().popBackStack();
    enableActivity();

    Toast.makeText(this, "A message showing how to reset your password has been send to your email."
            , Toast.LENGTH_LONG).show();
  }

  @Override
  public void onSignupFragmentInteraction(Uri uri) {

  }

  @Override
  public void onForgotPasswordFragmentInteraction(Uri uri) {

  }

  @Override
  public  void onBackPressed() {
    //super.onBackPressed();
    if ( getSupportFragmentManager().getBackStackEntryCount() == 0 ) {
      moveTaskToBack(true);
    } else {
      getSupportFragmentManager().popBackStack();
      // check if the login button has not disappeared!
      enableActivity();
    }
  }
}
