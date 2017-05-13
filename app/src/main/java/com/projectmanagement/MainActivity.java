package com.projectmanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.steamcrafted.materialiconlib.MaterialIconView;

import login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView user, inbox, projects, tasks;
    private TextView tnotification, tuser, tinbox, tprojects, ttask;
    private MaterialIconView notification;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tnotification = (TextView) findViewById(R.id.tvnotification);
        tuser = (TextView) findViewById(R.id.tvuser);
        tinbox = (TextView) findViewById(R.id.tvinbox);
        tprojects = (TextView) findViewById(R.id.tvproject);
        ttask = (TextView) findViewById(R.id.tvtask);

        onclick1();
        onclick2();
        onclick3();
        onclick4();
        onclick5();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    Context context = this;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logout:
                new AlertDialog.Builder(this).setTitle("Logout")
                        .setMessage("Would you like to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // logout
                                startActivity(new Intent(context, LoginActivity.class));
                                finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to logout
                    }
                }).show();
                break;

            case R.id.setting:
                startActivity(new Intent(context,ProfileSetting.class));
                break;

        }

        return true;
    }

    public void onclick1() {
        notification = (MaterialIconView) findViewById(R.id.ivnotification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Notification.class);
                startActivity(intent1);
            }
        });
    }

    public void onclick2() {
        user = (ImageView) findViewById(R.id.ivuser);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, User.class);
                startActivity(intent2);
            }
        });
    }

    public void onclick3() {
        inbox = (ImageView) findViewById(R.id.ivinbox);
        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this, Inbox.class);
                startActivity(intent3);
            }
        });
    }

    public void onclick4() {
        projects = (ImageView) findViewById(R.id.ivproject);
        projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MainActivity.this, Projects.class);
                startActivity(intent4);
            }
        });
    }

    public void onclick5() {
        tasks = (ImageView) findViewById(R.id.ivtask);
        tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(MainActivity.this, Tasks.class);
                startActivity(intent5);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("Logout")
                .setMessage("Would you like to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // logout
                        startActivity(new Intent(context, LoginActivity.class));
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // user doesn't want to logout
            }
        }).show();
    }
}
