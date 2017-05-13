package com.projectmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import Admin.AddNotification;
import Database.ExtrenalDB;
import Model.InboxcModel;

public class Notification extends AppCompatActivity {

    ListView list;
    NotificationAdapter adapter;
    Context context = this;
    FloatingActionButton fabAdd;
    //Recyclerview

    //RecyclerView recycler;

    //NotificationAdapter_Recycler adapter_recycler;

    ExtrenalDB db;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getTheme().applyStyle(R.style.ThemeNotification, true);

        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notification");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        init();

        dbNotification = FirebaseDatabase.getInstance().getReference("NotificationDetail");
    }

    HashMap<String,HashMap<String,String>> allData = new HashMap<>();
    DatabaseReference dbNotification;

    private void getData() {
        pd = new ProgressDialog(context);
        pd.setMessage("Please wait");

        pd.show();
        dbNotification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allData = (HashMap<String,HashMap<String,String>>) dataSnapshot.getValue();
                pd.dismiss();

                if (allData == null)
                    allData = new HashMap<>();
                setAdapter();
//                new JSONObject( ((ArrayList<Integer>) dataSnapshot.getValue()).get(1).toString())
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.dismiss();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    ArrayList<InboxcModel> data = new ArrayList<>();

    private void init() {

        db = new ExtrenalDB(context);
        data = db.getData();

        //Listview Steps
        setAdapter();
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);

        if (AppHeart.UserType.equalsIgnoreCase("0")) {
            fabAdd.setVisibility(View.VISIBLE);
        }
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddNotification.class));
            }
        });

    }

    private void setAdapter() {
        if (allData.size() > 0) {
            list = (ListView) findViewById(R.id.list);
            adapter = new NotificationAdapter(context, allData);
            list.setAdapter(adapter);
        }
    }

}
