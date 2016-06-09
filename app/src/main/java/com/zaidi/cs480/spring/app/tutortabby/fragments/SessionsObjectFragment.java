package com.zaidi.cs480.spring.app.tutortabby.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *  Sessions will be placed in this class.
 */
public class SessionsObjectFragment extends Fragment {
  private OnSessionsObjectFragmentListener callback;
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

  @Override
  public void onAttach(Context activity) {
    super.onAttach(activity);
    try {
      callback = (OnSessionsObjectFragmentListener) activity;
      openSessionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          callback.onSessionsObjectFragmentInteraction();
        }
      });
    } catch (Exception e) {

    }
  }

  /**
   * Grabs the query for the number of sessions that user currently has.
   * @return
   */
  private ArrayAdapter<listItem> getListAdapter() {
    ArrayAdapter<listItem>  listItemArrayAdapter = new ArrayAdapter<>(this.getActivity()
            , R.layout.simplerow);


    // Query current sessions with tutor
    String smnt = "select * from (select sessionDate as date, sessionSubject as subject, sessionDuration as duration, tutorID as tutor, studentID as student from sessions where ";
    switch (userType) {
      case "tutor":
        smnt = smnt.concat("tutorID=" + id + ") as t");
        break;
      case "student":
        smnt = smnt.concat("studentID=" + id + ") as t");
        break;
      default:
        smnt = "";
        break;
    }

    try {
      DBLoginActivity act = new DBLoginActivity(this.getActivity());
      act.execute("exe", smnt);

      ResultSet result = act.get();
      ArrayList<CharSequence> ids = new ArrayList<>();

      if (result != null) {
        while (result.next()) {
          String query = "";
          if (userType.equals("student")) {
            ids.add(result.getString("tutor"));
            query = "select * from (select tutorName as tutor from tutor where tutorID=" + result.getString("tutor") + ") as t";
          } else {
            ids.add(result.getString("student"));
            query = "select * from (select studentName as student from student where studentID=" + result.getString("student") + ") as t";
          }

          DBLoginActivity act2 = new DBLoginActivity(this.getActivity());
          act2.execute("exe", query);

          ResultSet name = act2.get();
          String userName = "";
          while(name.next()) {
            if (userType.equals("student")) {
              userName = name.getString("tutor");
              listItemArrayAdapter.add(new listItem(id, "Tutor name: " + userName
                      + "\nSession date: "
                      + result.getString("date")
                      + "\nSession Subject: "
                      + result.getString("subject")
                      + "\nSession Duration: "
                      + result.getString("duration") + "\n\n"));
            } else {
              userName = name.getString("student");
              listItemArrayAdapter.add(new listItem(id, "Student name: " + userName
                      + "\nSession date: "
                      + result.getString("date")
                      + "\nSession Subject: "
                      + result.getString("subject")
                      + "\nSession Duration: "
                      + result.getString("duration") + "\n\n"));
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listItemArrayAdapter;
  }

  public interface OnSessionsObjectFragmentListener {
    void onSessionsObjectFragmentInteraction();
  }

}