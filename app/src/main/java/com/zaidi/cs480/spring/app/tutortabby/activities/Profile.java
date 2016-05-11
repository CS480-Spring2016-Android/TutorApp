package com.zaidi.cs480.spring.app.tutortabby.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zaidi.cs480.spring.app.tutortabby.R;
import com.zaidi.cs480.spring.app.tutortabby.Search;
import com.zaidi.cs480.spring.app.tutortabby.adapters.NavItemAdapter;
import com.zaidi.cs480.spring.app.tutortabby.items.NavItem;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Profile extends Activity implements ListView.OnItemClickListener{
  /*
    TODO(Romero): This layout will need to be used for the home screen.
                  Once the home screen is complete, this slide menu will be
                  implemented onto it for proper navigation.
   */
  private DrawerLayout mDrawerLayout;
  private ListView mDrawerList;
  private ActionBarDrawerToggle mDrawerToggle;
  private CharSequence mDrawerTitle;
  private CharSequence mTitle;

  private String[] navMenuTitles;
  private TypedArray navMenuIcons;

  private ArrayList<NavItem> navDrawerItems;
  private NavItemAdapter adapter;

  private TextView t = null;
  private int id;
  private static final String URL_LINK = "jdbc:mariadb://db.zer0-one.net/tutorWeb";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle b = getIntent().getExtras();
    id = b.getInt("uid");

    setContentView(R.layout.activity_sample_menu);

    t = (TextView)findViewById(R.id.data);

    t.setText("Downloading");

    try {
      String query = "select tutorName, tutorEmail from tutor where tutorID = " + id;
      new DownloadWebpageTask().execute(query);
    }
    catch (Exception e){
      t.setText(e.toString());
    }

    mTitle = mDrawerTitle = getTitle();

    navMenuTitles = getResources().getStringArray(R.array.nav_item_drawer_strings);
    navMenuIcons = getResources().obtainTypedArray(R.array.nav_item_drawer_icons);

    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

    navDrawerItems = new ArrayList<>();

    navDrawerItems.add(new NavItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
    navDrawerItems.add(new NavItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
    navDrawerItems.add(new NavItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
    navDrawerItems.add(new NavItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
    navDrawerItems.add(new NavItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
    navDrawerItems.add(new NavItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

//      t.setText(navDrawerItems.get(0).getTitle());
    t.setText(Integer.toString(id));

    navMenuIcons.recycle();
    adapter = new NavItemAdapter(getApplicationContext(), navDrawerItems);
    mDrawerList.setAdapter(adapter);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);
    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
            null,
            R.string.app_name,
            R.string.app_name) {
      public void onDrawerClosed(View view) {
        getActionBar().setTitle(mTitle);
        invalidateOptionsMenu();
      }

      public void onDrawerOpened(View drawerView) {
        getActionBar().setTitle(mDrawerTitle);
        invalidateOptionsMenu();

      }
    };
    mDrawerLayout.setDrawerListener(mDrawerToggle);
    mDrawerList.setOnItemClickListener(this);

    if (savedInstanceState == null) {
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // toggle nav drawer on selecting action bar app icon/title
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    // Handle action bar actions click
    switch (item.getItemId()) {
      case R.id.action_settings:
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }

  }

  /***
   * Called when invalidateOptionsMenu() is triggered
   */
  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    // if nav drawer is opened, hide the action items
    boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
    menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public void setTitle(CharSequence title) {
    mTitle = title;
    getActionBar().setTitle(mTitle);
  }

  /**
   * When using the ActionBarDrawerToggle, you must call it during
   * onPostCreate() and onConfigurationChanged()...
   */

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    // Sync the toggle state after onRestoreInstanceState has occurred.
    mDrawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    // Pass any configuration change to the drawer toggls
    mDrawerToggle.onConfigurationChanged(newConfig);
  }

  //Async task to download from database

  private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<String>> {
      Bitmap img;
    ArrayList<String> result = new ArrayList<>();

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
      // params comes from the execute() call: params[0] is the url.
      try {
        Class.forName("org.mariadb.jdbc.Driver").newInstance();
        String username = "jacobromero";
        String password = "uish6ahK";

        Connection DBconn = DriverManager.getConnection(URL_LINK, username, password);
        Log.w("Connection", "open");

        Statement stmnt = DBconn.createStatement();
        ResultSet resultSet = stmnt.executeQuery(strings[0]);

        resultSet.next();

        result.add(resultSet.getString("tutorName"));
        result.add(resultSet.getString("tutorEmail"));
      } catch (SQLException e) {
        result.add(e.toString());
        result.add(e.toString());
      }
      catch (Exception e){
        result.add(e.toString());
        result.add(e.toString());
      }

      return result;
    }

    protected void onPostExecute(ArrayList<String> result) {
      TextView user = (TextView) findViewById(R.id.userName);
      TextView email = (TextView) findViewById(R.id.role);
      try {
          user.setText(result.get(0));
          email.setText(result.get(1));
      }
      catch (Exception e){
        t.setText(e.toString());
      }
    }
  }

    public void onItemClick(AdapterView parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position){
        switch (position){
            case 1:
                Intent intent = new Intent(this, Search.class);
                startActivity(intent);
                break;
        }
    }

  /**
   * To keep having this profile show up, and to prevent the application from resetting if user
   * decides to go into another application, this function must be called in order to retain the
   * Activity while it is moved to the back process.
   */
  @Override
  public void onBackPressed() {
    moveTaskToBack(true);
  }
}
