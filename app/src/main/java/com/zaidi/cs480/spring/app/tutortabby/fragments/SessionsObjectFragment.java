package com.zaidi.cs480.spring.app.tutortabby.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zaidi.cs480.spring.app.tutortabby.R;
import com.zaidi.cs480.spring.app.tutortabby.listItem;

/**
 *  Sessions will be placed in this class.
 */
public class SessionsObjectFragment extends Fragment {
  private TextView nothingToShow;
  private ListView sessionsList;
  private ArrayAdapter<listItem> listAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_sessions, container, false);
    nothingToShow = (TextView) rootView.findViewById(R.id.sessions_nothing_to_show);
    nothingToShow.setVisibility(View.GONE);

    sessionsList = (ListView) rootView.findViewById(R.id.sessions_display);
    listAdapter = new ArrayAdapter<>(this.getActivity(), R.layout.simplerow);

    sessionsList.setAdapter(listAdapter);

    if (listAdapter.isEmpty()) {
      nothingToShow.setVisibility(View.VISIBLE);
    }

    return rootView;
  }
}