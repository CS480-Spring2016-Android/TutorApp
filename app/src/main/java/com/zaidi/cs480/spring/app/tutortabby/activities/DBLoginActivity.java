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
public class DBLoginActivity extends AsyncTask<String, Void, Integer> {

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
  protected Integer doInBackground(String... params) {
    Connection DBconn = null;
    int id = -1;
    try {
      Class.forName("org.mariadb.jdbc.Driver").newInstance();
      String username = "jacobromero";
      String password = "uish6ahK";

      DBconn = DriverManager.getConnection(URL_LINK, username, password);
      Log.w("Connection", "open");

      Statement stmnt = DBconn.createStatement();

      //TODO this will search in both databases, however only one account is considered if the person is both a student and tutor with same user name and password this needs to be fixed at some point
      String sql = "select t.id, t.name, t.source, count(*) as count from ((select tutorID as id, tutorName as name, 'tutor' as source from tutor where tutorName = \"" + params[0] + "\" and tutorPassword = \"" + params[1] + "\") union (select studentID as id, studentName as name, 'student' as source from student where studentName = \"" + params[0] + "\" and studentPassword = \"" + params[1] + "\")) t group by t.name";

      ResultSet resultSet = stmnt.executeQuery(sql);


      if(!resultSet.next() && resultSet.getInt("count") != 0) {
        return -1;
      }

      id = resultSet.getInt("id");
      // Close the DB just for testing purposes.
      DBconn.close();
      loginSuccess = resultSet.next();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      lastErrorString = e.getMessage();
      loginSuccess = false;
    }
    return id;
  }


  protected void onPostExecute(Boolean Result) {

  }
}
