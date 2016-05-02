package com.zaidi.cs480.spring.app.tutortabby.activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.zaidi.cs480.spring.app.tutortabby.R;
import com.zaidi.cs480.spring.app.tutortabby.adapters.NavItemAdapter;
import com.zaidi.cs480.spring.app.tutortabby.items.NavItem;

import java.util.ArrayList;

public class SampleMenuActivity extends Activity {
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_menu);

    mTitle = mDrawerTitle = getTitle();

    navMenuTitles = getResources().getStringArray(R.array.nav_item_drawer_strings);
    navMenuIcons = getResources().obtainTypedArray(R.array.nav_item_drawer_icons);

    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

    navDrawerItems = new ArrayList<>();

    navDrawerItems.add(new NavItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
    navDrawerItems.add(new NavItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
    navDrawerItems.add(new NavItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));

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
}