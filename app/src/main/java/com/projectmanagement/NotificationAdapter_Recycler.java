package com.projectmanagement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;

import Model.InboxcModel;

/**
 * Created by Babul Patel on 02/04/2017.
 */

public class NotificationAdapter_Recycler extends RecyclerView.Adapter<NotificationAdapter_Recycler.ViewHolder> {

    LayoutInflater inflater;
    Context context;

    ArrayList<InboxcModel> data;

    public NotificationAdapter_Recycler(Context context, ArrayList<InboxcModel> c_data) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        data = c_data;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_notification_row, null);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.content.setText(data.get(position).getContent());
        holder.title.setText(data.get(position).getTitle());

        holder.clickLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialIconView icon;
        TextView title, content;
        LinearLayout clickLay;

        public ViewHolder(View itemView) {
            super(itemView);

            icon = (MaterialIconView) itemView.findViewById(R.id.ivnotification);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            clickLay = (LinearLayout) itemView.findViewById(R.id.clickLay);
        }
    }
}
