package com.projectmanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Database.ExtrenalDB;
import Model.UserModel;

public class User extends AppCompatActivity {
    ListView list;
    UserAdapter adapter;
    Context context = this;
    ExtrenalDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTheme().applyStyle(R.style.ThemeUsers,true);

        setContentView(R.layout.activity_user);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Users");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);

    }

    ArrayList<UserModel> data = new ArrayList<>();
    HashMap<String,HashMap<String,String>>  livedata = new HashMap<>();

    private void init() {
        db = new ExtrenalDB(context);
//        data = db.getData4();

        list = (ListView) findViewById(R.id.userlist);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        getData();
    }

    DatabaseReference dbRef, dbRef_UserList;

    private void getData(){

        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        dbRef_UserList = FirebaseDatabase.getInstance().getReference("Notifications");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                livedata = (HashMap<String,HashMap<String,String>> )dataSnapshot.getValue();

                adapter = new UserAdapter(context, livedata);
                list.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void sendNotificationToUser(String user, final String message) {

        Map notification = new HashMap<>();
        notification.put("title", user);
        notification.put("clientid",AppHeart.ClientID);
        notification.put("message", message);

        dbRef_UserList.push().setValue(notification);
    }

}
