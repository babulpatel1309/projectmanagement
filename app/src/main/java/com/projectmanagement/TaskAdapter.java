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

public class TaskAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    //    List<TaskModel> data;
    HashMap<String, HashMap<String, String>> allData;
    ArrayList<String> keys = new ArrayList<>();

    public TaskAdapter(Context context, HashMap<String, HashMap<String, String>> allData) {
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
        convertView = inflater.inflate(R.layout.custom_task_row, null);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView status = (TextView) convertView.findViewById(R.id.status);


        try {
            HashMap<String, String> jsonObject = allData.get(keys.get(position));

            title.setText(jsonObject.get("title"));

            if (jsonObject.get("status").equalsIgnoreCase("0")) {
                status.setText("Pending");
            } else if (jsonObject.get("status").equalsIgnoreCase("1")) {
                status.setText("Running");
            } else {
                status.setText("Completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
