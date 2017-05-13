package Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projectmanagement.R;

/**
 * Created by Babul Patel on 22/04/2017.
 */

public class AdminBase extends AppCompatActivity {

    Context context;
    DatabaseReference dbRef;
    ProgressDialog pd;

    static String dbRefProjects =  "ProjectsDetail";
    static String dbRefNotification =  "NotificationDetail";
    static String dbRefTasks =  "TasksDetail";
    static String dbRefInbox =  "InboxDetail";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;



        pd = new ProgressDialog(context);
        pd.setTitle("Adding... Please wait");
    }

    public void getDb(String name){
        dbRef = FirebaseDatabase.getInstance().getReference(name);
    }

    public void setTitlebar(String title){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
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

}
