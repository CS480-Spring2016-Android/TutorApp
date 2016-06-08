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
public class DBLoginActivity extends AsyncTask<String, Void, ResultSet> {

  public Boolean Success;
  private Context context;
  private static final String URL_LINK = "jdbc:mariadb://db.zer0-one.net/tutorWeb";
  private ResultSet resultSet;

  private String lastErrorString;

  public DBLoginActivity(Context context) {
    Success = false;
    this.context = context;
    lastErrorString = "NONE";
  }

  @Override
  protected ResultSet doInBackground(String... params) {
    Connection DBconn = null;
    int id = -1;
    try {
      Class.forName("org.mariadb.jdbc.Driver").newInstance();
      String username = "jacobromero";
      String password = "uish6ahK";

      DBconn = DriverManager.getConnection(URL_LINK, username, password);
      Log.w("Connection", "open");

      Statement stmnt = DBconn.createStatement();

      if(params[0].compareTo("exe") == 0)
        resultSet = stmnt.executeQuery(params[1]);
      else
        stmnt.executeUpdate(params[1]);

      DBconn.close();

    } catch (Exception e) {
      System.out.println(e.getMessage());
      lastErrorString = e.getMessage();
      Success = false;
    }

    return resultSet;
  }


  protected void onPostExecute(Boolean Result) {
    Success = true;
  }
}
