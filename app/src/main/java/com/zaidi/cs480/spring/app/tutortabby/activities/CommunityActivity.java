package com.zaidi.cs480.spring.app.tutortabby.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zaidi.cs480.spring.app.tutortabby.R;
import com.zaidi.cs480.spring.app.tutortabby.adapters.NavItemAdapter;
import com.zaidi.cs480.spring.app.tutortabby.items.NavItem;
import com.zaidi.cs480.spring.app.tutortabby.listItem;
import com.zaidi.cs480.spring.app.tutortabby.fragments.CommunityObjectFragment;
import com.zaidi.cs480.spring.app.tutortabby.fragments.NewsObjectFragment;
import com.zaidi.cs480.spring.app.tutortabby.fragments.SessionsObjectFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * CommunityActivity is an Activity holding three fragments, one to store and display news, the other
 * to display current sessions for each tutor, as well as open lobbies for which the user may be able
 * to enter and chat with friends and tutors alike.
 */
public class CommunityActivity extends FragmentActivity implements ListView.OnItemClickListener {
  /**
   * Layout of the drawer. Drawn on the outside of the screen. It is then displayed when
   * user attempts to swipe from the left edge of the screen.
   */
  private DrawerLayout mDrawerLayout;
  /**
   * List display of all availabled elements that the user can have access to. User may be able to
   * click on any of these elements within the list.
   */
  private ListView mDrawerList;
  /**
   * Toggle button on top, within the action bar.
   */
  private ActionBarDrawerToggle mDrawerToggle;
  /**
   * Title of the drawer layout, otherwise known as the current Activity Title.
   */
  private CharSequence mDrawerTitle;
  /**
   * Navigational menu titles within the DrawerList.
   */
  private String[] navMenuTitles;
  /**
   * Navigational icons corresponding to the menu titles within DrawerList.
   */
  private TypedArray navMenuIcons;
  /**
   * List of All item containers within the DrawerList.
   */
  private ArrayList<NavItem> navDrawerItems;
  /**
   * Adapter for the DrawerList.
   */
  private NavItemAdapter adapter;
  /**
   * List View for the main list?
   */
  private ListView mainListView;
  /**
   * This is never used...
   */
  private ArrayAdapter<listItem> listAdapter;

  /**
   * Used to store the last screen title. For use in.
   */
  private CharSequence mTitle;

  /**
   * Page adapter for fragments
   */
  CommunityPagerAdapter mPageAdapter;
  /**
   * For viewing the Pager within the layout.
   */
  ViewPager mViewPager;
  /**
   * Logged in user id.
   */
  private int id;
  /**
   * logged in user type. { tutor, student } struct.
   */
  private String userType;

  /**
   * Sets up the drawerLayout, list, and adapter. Pager for each fragment is also constructed after
   * as well as information from LoginActivity.
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_community);

    savedInstanceState = getIntent().getExtras();
    id = savedInstanceState.getInt("uid");
    // Should be checked for null!!
    userType = savedInstanceState.getString("userType");

    mTitle = mDrawerTitle = getTitle();

    navMenuTitles = getResources().getStringArray(R.array.nav_item_drawer_strings);
    navMenuIcons = getResources().obtainTypedArray(R.array.nav_item_drawer_icons);

    mDrawerLayout = (DrawerLayout) findViewById(R.id.community_layout);
    mDrawerList = (ListView) findViewById(R.id.list_slidermenu_search);

    navDrawerItems = new ArrayList<>();

    navDrawerItems.add(new NavItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
    navDrawerItems.add(new NavItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
    navDrawerItems.add(new NavItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
    navDrawerItems.add(new NavItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
    navDrawerItems.add(new NavItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
    navDrawerItems.add(new NavItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

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



    mPageAdapter = new CommunityPagerAdapter(
            getSupportFragmentManager());
    mViewPager = (ViewPager) findViewById(R.id.pager);
    mViewPager.setAdapter(mPageAdapter);
  }

  /**
   * Open the Options menu, Allows dynamic navigation to the settings menu.
   * @param menu
   * @return
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  /**
   * Buffer to switch to a different activity.
   * @param parent
   * @param view
   * @param position
   * @param id
   */
  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    selectItem(position);
  }

  /**
   * Register the selected item within the menu, NOT the drawerList.
   * @param item
   * @return
   */
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

  /**
   * Set the title within the actionbar.
   * @param title
   */
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

  /**
   * Configure the user input, when user swipes across screen.
   * @param newConfig
   */
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    // Pass any configuration change to the drawer toggls
    mDrawerToggle.onConfigurationChanged(newConfig);
  }

  /**
   * PagerAdapter that holds each and every fragment within the tabs that are provided to the user.
   */
  public class CommunityPagerAdapter extends FragmentStatePagerAdapter {
    /**
     * Titles of each tab.
     */
    private CharSequence[] titles;

    /**
     * Constructor that will initialize the charSequence.
     * @param fm
     */
    public CommunityPagerAdapter(FragmentManager fm) {
      super(fm);

      titles = new CharSequence[3];
      titles[0] = "News";
      titles[1] = "Sessions";
      titles[2] = "Lobbies";
    }

    /**
     * Grab our item, in this case, the fragments within.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
      Fragment fragment;
      Bundle args = new Bundle();
      switch (position) {
        case 0:
          fragment = new NewsObjectFragment();
          break;
        case 1:
          args.putInt("uid", id);
          args.putString("userType", userType);
          fragment = new SessionsObjectFragment();
          break;
        default:
          fragment = new CommunityObjectFragment();
      }
      args.putInt(CommunityObjectFragment.ARG_OBJECT, position + 1 );
      fragment.setArguments(args);
      return fragment;
    }

    /**
     * Grab the number of fragments in tab.
     * @return
     */
    @Override
    public int getCount() {
      return 3;
    }

    /**
     * Get the title of the page.
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
      return titles[(position)];
    }
  }

  /**
   * Select item will prevent the user from making more pages than there needs to be. I will return
   * to the Profile page, and execute the next page that the user requested.
   * @param position
   */
  private void selectItem(int position) {
    Intent intent = new Intent();
    String value = "-1";
    switch (position) {
      case 0:
        value = "0";
        break;
      case 1:
        value = "1";
        break;
      case 2:
        value = "2";
        break;
      case 3:
        // already at community!!
        intent = null;
        break;
      case 4:
        value = "4";
        break;
      case 5:
        value = "5";
        break;
      default:
        intent = null;
        break;
    }

    if (intent != null) {
      intent.putExtra("value", value);
      setResult(RESULT_OK, intent);
      finish();
    }
  }
}
