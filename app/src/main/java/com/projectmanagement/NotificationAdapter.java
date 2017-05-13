package com.projectmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Babul Patel on 02/04/2017.
 */

public class NotificationAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
//    List<InboxcModel> data;

    HashMap<String,HashMap<String,String>> allData;
    ArrayList<String> keys = new ArrayList<>();

    public NotificationAdapter(Context context, HashMap<String,HashMap<String,String>> allData) {
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
        return keys.size();
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
        convertView = inflater.inflate(R.layout.custom_notification_row, null);
        MaterialIconView icon = (MaterialIconView) convertView.findViewById(R.id.ivnotification);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView content = (TextView) convertView.findViewById(R.id.content);

        try {
            HashMap<String, String> jsonObject = allData.get(keys.get(position));

            title.setText(jsonObject.get("title"));
            content.setText(jsonObject.get("content"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }
}
