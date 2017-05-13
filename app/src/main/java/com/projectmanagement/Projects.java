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

import Admin.AddProject;
import Database.ExtrenalDB;
import Model.ProjectModel;

public class Projects extends AppCompatActivity {
    ListView list;
    ProjectAdapter adapter;
    Context context = this;
    ExtrenalDB db;

    FloatingActionButton fabAdd;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getTheme().applyStyle(R.style.ThemeProject,true);

        setContentView(R.layout.activity_projects);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Projects");
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

    ArrayList<ProjectModel> data = new ArrayList<>();

    private void init() {
        db = new ExtrenalDB(context);
        data = db.getData3();

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        if (AppHeart.UserType.equalsIgnoreCase("0")) {
            fabAdd.setVisibility(View.VISIBLE);
        }
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddProject.class));
            }
        });

        getData();
    }


    HashMap<String,HashMap<String,String>> allData = new HashMap<>();
    DatabaseReference dbRef;

    private void getData() {
        pd = new ProgressDialog(context);
        pd.setMessage("Please wait");

        dbRef = FirebaseDatabase.getInstance().getReference("ProjectsDetail");

        pd.show();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allData = (HashMap<String,HashMap<String,String>>) dataSnapshot.getValue();
                pd.dismiss();

                if (allData == null)
                    allData = new HashMap<>();
                setAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.dismiss();
            }
        });
    }

    private void setAdapter() {
        if (allData.size() > 0) {
            list = (ListView) findViewById(R.id.projectlist);
            adapter = new ProjectAdapter(context, allData);
            list.setAdapter(adapter);
        }
    }

}
