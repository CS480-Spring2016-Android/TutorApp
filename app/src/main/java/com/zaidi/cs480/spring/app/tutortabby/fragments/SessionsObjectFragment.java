package com.zaidi.cs480.spring.app.tutortabby.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zaidi.cs480.spring.app.tutortabby.R;
import com.zaidi.cs480.spring.app.tutortabby.activities.DBLoginActivity;
import com.zaidi.cs480.spring.app.tutortabby.listItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

/**
 *  Sessions will be placed in this class.
 */
public class SessionsObjectFragment extends Fragment {
  private TextView nothingToShow;
  private ListView sessionsList;
  private ArrayAdapter<listItem> listAdapter;

  private int id;
  private String userType;

  private Button openSessionButton;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_sessions, container, false);
    nothingToShow = (TextView) rootView.findViewById(R.id.sessions_nothing_to_show);
    openSessionButton = (Button) rootView.findViewById(R.id.open_session_button);
    openSessionButton.setVisibility(View.GONE);
    openSessionButton.setEnabled(false);
    nothingToShow.setVisibility(View.GONE);

    Bundle args = getArguments();
    if (args != null) {
      id = args.getInt("uid");
      userType = args.getString("userType");
    } else {
      userType = "none";
      id = Integer.MAX_VALUE;
    }

    sessionsList = (ListView) rootView.findViewById(R.id.sessions_display);
    listAdapter = getListAdapter();

    sessionsList.setAdapter(listAdapter);

    if (listAdapter.isEmpty()) {
      nothingToShow.setVisibility(View.VISIBLE);
      openSessionButton.setVisibility(View.VISIBLE);
      openSessionButton.setEnabled(true);
    }

    return rootView;
  }

  /**
   * Grabs the query for the number of sessions that user currently has.
   * @return
   */
  private ArrayAdapter<listItem> getListAdapter() {
    ArrayAdapter<listItem>  listItemArrayAdapter = new ArrayAdapter<>(this.getActivity()
            , R.layout.simplerow);


    // Query current sessions with tutor
    String smnt = "select * from (select sessionDate as date, sessionSubject as subject, sessionDuration as duration from sessions where ";
    switch (userType) {
      case "tutor":
        smnt = smnt.concat("tutorID=\'" + id + "\')");
        break;
      case "student":
        smnt = smnt.concat("studentID=\'" + id + "\')");
        break;
      default:
        smnt = "";
        break;
    }

    try {
      DBLoginActivity act = new DBLoginActivity(this.getActivity());
      act.execute(smnt);

      ResultSet result = act.get();

      if (result != null) {
        while (result.next()) {
          listItemArrayAdapter.add(new listItem(id, "Session date: "
                  + result.getString("date")
                  + "\nSession Subject: "
                  + result.getString("subject")
                  + "\nSession Duration: "
                  + result.getString("duration") + "\n\n"));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listItemArrayAdapter;
  }
}