package com.zaidi.cs480.spring.app.tutortabby.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zaidi.cs480.spring.app.tutortabby.R;

/**
 * No News, but it will be here anyway...
 */
public class NewsObjectFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_news, container, false);
    Bundle args = getArguments();
    return rootView;
  }
}