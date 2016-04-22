package com.zaidi.cs480.spring.app.tutortabby.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zaidi.cs480.spring.app.tutortabby.R;

import java.util.zip.Inflater;

/**
 * Created by MAGarcia on 4/22/2016.
 */
public class ForgotPasswordFragment extends Fragment {

  private OnForgotPasswordFragmentListener mListener;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle os) {
    return inflater.inflate(R.layout.fragment_lost_password, container, false);
  }

  @Override
  public void onCreate(Bundle os) {
    super.onCreate(os);

  }

  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnForgotPasswordFragmentListener) {
      mListener = (OnForgotPasswordFragmentListener) context;
    } else {
      throw new RuntimeException(context.toString()
              + " must implement OnForgotPasswordFragmentListener.");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  // Interaction with this fragment.
  public interface OnForgotPasswordFragmentListener {

    void onFragmentInteraction(Uri uri);
  }
}
