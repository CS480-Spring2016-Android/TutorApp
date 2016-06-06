package com.zaidi.cs480.spring.app.tutortabby;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zaidi.cs480.spring.app.tutortabby.adapters.NavItemAdapter;
import com.zaidi.cs480.spring.app.tutortabby.items.NavItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class Search extends Activity implements ListView.OnItemClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
//    private NavigationDrawerFragment mNavigationDrawerFragment;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavItem> navDrawerItems;
    private NavItemAdapter adapter;
    private String searchText = "";

    private ListView mainListView;
    private ArrayAdapter<listItem> listAdapter;
    /**
     * Used to store the last screen title. For use in.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        mTitle = mDrawerTitle = getTitle();

        navMenuTitles = getResources().getStringArray(R.array.nav_item_drawer_strings);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_item_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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


        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mainListView = (ListView) findViewById( R.id.searchResults );


        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<listItem>(this, R.layout.simplerow);

        mainListView.setAdapter( listAdapter );

        registerForContextMenu(mainListView);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");

                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] {listAdapter.getItem(position).email});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Tutoring?");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"");

                startActivity(emailIntent);
            }
        });
    }

    //create context menu here
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.searchResults) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("something");
            String[] menuItems = {"1", "2", "3"};
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    //set context menu on click here
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = {"1","2","3"};
        String menuItemName = menuItems[menuItemIndex];
        String listItemName = "something";

        Toast.makeText(getApplicationContext(), menuItemName + " " + listItemName, Toast.LENGTH_LONG).show();
        return true;
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


//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_search, container, false);
            return rootView;
        }
    }

    private void search(){
        EditText t = (EditText) findViewById(R.id.searchBox);

        searchText = t.getText().toString();

        listAdapter.clear();

        new DownloadWebpageTask().execute();
    }


    private class DownloadWebpageTask extends AsyncTask<Void, Void, ResultSet> {
        private static final String URL_LINK = "jdbc:mariadb://db.zer0-one.net/tutorWeb";
        ArrayList<String> result = new ArrayList<>();
        ResultSet resultSet;

        @Override
        protected ResultSet doInBackground(Void... voids) {
            // params comes from the execute() call: params[0] is the url.
            try {
                Class.forName("org.mariadb.jdbc.Driver").newInstance();
                String username = "jacobromero";
                String password = "uish6ahK";

                Connection DBconn = DriverManager.getConnection(URL_LINK, username, password);
                Log.w("Connection", "open");

                Statement stmnt = DBconn.createStatement();

                //TODO this will only search given fields, empty search returns all results, may need to limit
                String sql = "Select t.id, t.name, t.email, t.subject, t.source from ((select tutorID as id, tutorName as name, "
                        + "tutorEmail as email, 'Tutor' as source, tutorSubjects as subject from tutor where tutorname like \"%"
                        + searchText +"%\" or tutoremail like \"%"
                        + searchText + "%\" or tutorSubjects like \"%"
                        + searchText + "%\") union (select studentID as id, studentName as name, studentEmail as "
                        + "email, 'Student' as source, studentSubjects as subject from student where studentname like \"%"
                        + searchText + "%\" or studentemail like \"%"
                        + searchText + "%\" or studentSubjects like \"%"
                        + searchText + "%\")) t order by t.name";

                return stmnt.executeQuery(sql);


            } catch (SQLException e) {
                result.add(e.toString());
                result.add(e.toString());
            }
            catch (Exception e){
                result.add(e.toString());
                result.add(e.toString());
            }

            return resultSet;
        }

        protected void onPostExecute(ResultSet result) {
//            TextView tv = (TextView) findViewById(R.id.searchResults);
            try {
//                tv.setText("");
                while(result.next()){
                    listAdapter.add(new listItem(result.getInt("id"), "Name: " + result.getString("name") + "\n\nEmail: " + result.getString("email") + "\n\nRole: " + result.getString("source") + "\n\nSubjects: " + result.getString("subject"), result.getString("email")));
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    public void onItemClick(AdapterView parent, View view, int position, long id) {
        selectItem(position);
    }

  /**
   * Select item will prevent the user from making more pages than there needs to be. I will return
   * to the Profile page, and execute the next page that the user requested.
   * @param position
   */
  private void selectItem(int position){
        Intent intent = new Intent();
        String value = "-1";
        switch (position) {
            case 0:
                value = "0";
                break;
            case 1:
                intent = null;
                // Already at Search!!
                break;
            case 2:
                value = "2";
                break;
            case 3:
                value = "3";
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
