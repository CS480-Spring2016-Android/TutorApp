package com.zaidi.cs480.spring.app.tutortabby.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mariadb.jdbc.*;

/**
 * Created by MAGarcia on 5/8/2016.
 */
public class DBLoginActivity extends AsyncTask<String, Void, Boolean> {

  private Boolean loginSuccess;
  private Context context;
  private static final String URL_LINK = "jdbc:mariadb://db.zer0-one.net/tutorWeb";

  private String lastErrorString;

  DBLoginActivity(Context context) {
    loginSuccess = false;
    this.context = context;
    lastErrorString = "NONE";
  }

  @Override
  protected Boolean doInBackground(String... params) {
    Connection DBconn = null;
    try {
      Class.forName("org.mariadb.jdbc.Driver").newInstance();
      String username = "jacobromero";
      String password = "uish6ahK";

      DBconn = DriverManager.getConnection(URL_LINK, username, password);
      Log.w("Connection", "open");

      Statement stmnt = DBconn.createStatement();
      ResultSet resultSet = stmnt.executeQuery("select tutorID from tutor where tutorName = \"" + params[0] + "\" and tutorPassword = \"" + params[1] + "\"");


      if(!resultSet.next())
        return false;

      // Close the DB just for testing purposes.
      DBconn.close();
      loginSuccess = true;
    } catch (Exception e) {
      lastErrorString = e.getMessage();
      loginSuccess = false;
    }
    return loginSuccess;
  }

  @Override
  protected void onPostExecute(Boolean Result) {

  }
}
