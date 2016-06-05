package com.zaidi.cs480.spring.app.tutortabby.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zaidi.cs480.spring.app.tutortabby.R;

public class CommunityActivity extends FragmentActivity {
  CommunityPagerAdapter mPageAdapter;
  ViewPager mViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_community);

    mPageAdapter = new CommunityPagerAdapter(
            getSupportFragmentManager());
    mViewPager = (ViewPager) findViewById(R.id.pager);
    mViewPager.setAdapter(mPageAdapter);
  }

  public class CommunityPagerAdapter extends FragmentStatePagerAdapter {

    public CommunityPagerAdapter(FragmentManager fm) {
      super(fm);
    }
    @Override
    public Fragment getItem(int position) {
      Fragment fragment = new CommunityObjectFragment();
      Bundle args = new Bundle();
      args.putInt(CommunityObjectFragment.ARG_OBJECT, position + 1);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public int getCount() {
      return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return "OBJECT " + (position + 1);
    }
  }

  public static class CommunityObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_community_object, container, false);
      Bundle args = getArguments();
      ((TextView) rootView.findViewById(android.R.id.text1)).setText(
              Integer.toString(args.getInt(ARG_OBJECT)));

      return rootView;
    }
  }
}
