package com.zaidi.cs480.spring.app.tutortabby.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.zaidi.cs480.spring.app.tutortabby.R;

/**
 * Created by MAGarcia on 6/8/2016.
 */
public class OpenSessionsFragment extends Fragment {

  EditText tutorId;

  Spinner subjectSpinner;

  DatePicker datePicker;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_add_session, container, false);



    return rootView;
  }

  public interface OnOpenSessionsFragmentListener {

    void onSignupFragmentInteraction(Uri uri);
  }

  public void openSession() {

  }
}
