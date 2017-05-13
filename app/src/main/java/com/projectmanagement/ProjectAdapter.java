package com.projectmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Babul Patel on 03/04/2017.
 */

public class ProjectAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    //    List<ProjectModel> data;
    HashMap<String, HashMap<String, String>> allData;
    ArrayList<String> keys = new ArrayList<>();

    public ProjectAdapter(Context context, HashMap<String, HashMap<String, String>> allData) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.allData = allData;

        for (Map.Entry<String, HashMap<String, String>> entry : this.allData.entrySet()) {
            String key = entry.getKey();
            keys.add(key);
        }
    }

    @Override
    public int getCount() {
        return allData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_projects_row, null);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView status = (TextView) convertView.findViewById(R.id.status);


        try {
            HashMap<String, String> jsonObject = allData.get(keys.get(position));

            name.setText(jsonObject.get("name"));
            status.setText(jsonObject.get("status").equalsIgnoreCase("0") ? "Pending" : "Completed");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}

