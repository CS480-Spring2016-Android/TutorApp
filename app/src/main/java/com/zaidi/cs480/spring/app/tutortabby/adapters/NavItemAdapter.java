package com.zaidi.cs480.spring.app.tutortabby.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.zaidi.cs480.spring.app.tutortabby.R;
import com.zaidi.cs480.spring.app.tutortabby.items.NavItem;

import java.util.List;
/**
 * Created by MAGarcia on 4/25/2016.
 */
public class NavItemAdapter extends BaseAdapter {
  private Context context;
  private List<NavItem> navItemsList;

  public NavItemAdapter(Context context, List<NavItem> navItemsList) {
    this.context = context;
    this.navItemsList = navItemsList;
  }

  @Override
  public int getCount() {
    return navItemsList.size();
  }

  @Override
  public Object getItem(int position) {
    return navItemsList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      LayoutInflater mInflater = (LayoutInflater)
              context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
      convertView = mInflater.inflate(R.layout.drawer_list_item_nav, null);
    }

    ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
    TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
    TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

    imgIcon.setImageResource(navItemsList.get(position).getIcon());
    txtTitle.setText(navItemsList.get(position).getTitle());
    // Display count
    // Check whether it is set visible.
    if (navItemsList.get(position).getCounterVisibility()) {
      txtCount.setText(navItemsList.get(position).getCount());
    } else {
      // if not, hide the counter view.
      txtCount.setVisibility(View.GONE);
    }
    return convertView;
  }
}
