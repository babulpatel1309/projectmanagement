package com.projectmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Babul Patel on 03/04/2017.
 */

public class UserAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    HashMap<String, HashMap<String, String>> data;
    ArrayList<String> keys = new ArrayList<>();

    public UserAdapter(Context context, HashMap<String, HashMap<String, String>> data1) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = data1;

        for (Map.Entry<String, HashMap<String, String>> entry : data1.entrySet()) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_users_row, null);

        try {
            HashMap<String, String> jsonObject = data.get(keys.get(position));

            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView email = (TextView) convertView.findViewById(R.id.email);
            name.setText(jsonObject.get("Name"));
            email.setText(jsonObject.get("Email"));
            LinearLayout clickLay = (LinearLayout) convertView.findViewById(R.id.clickLay);

            clickLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HashMap<String, String> jsonObject = data.get(keys.get(position));

                    Intent intent = new Intent(context, UserDeatils.class);
                    intent.putExtra("Name", jsonObject.get("Name"));
                    context.startActivity(intent);

//                    ((User) context).sendNotificationToUser("Hello", "First Message");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
