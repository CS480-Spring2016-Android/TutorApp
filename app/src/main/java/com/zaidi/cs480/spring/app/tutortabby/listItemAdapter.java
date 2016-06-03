package com.zaidi.cs480.spring.app.tutortabby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob Romero on 6/2/2016.
 */
public class listItemAdapter extends ArrayAdapter<listItem> {
    private int resource;

    private static class ViewHolder {
        private TextView itemView;
    }

    public listItemAdapter(Context context, int resource, ArrayList<listItem> list){
        super(context, resource, list);
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        listItem li = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simplerow, parent, false);
        }

        // Lookup view for data population
        TextView tv = (TextView) convertView.findViewById(resource);

        // Populate the data into the template view using the data object
        tv.setText(li.text);

        // Return the completed view to render on screen
        return convertView;
    }

}
