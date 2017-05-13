package login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.projectmanagement.AppHeart;
import com.projectmanagement.R;

/**
 * Created by Babul Patel on 16/04/2017.
 */

public class ForgotPassword extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Context context = this;
    EditText etchangeEmail;
    CardView cardforget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);
        firebaseAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Forgot Password");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        init();
    }
   // public boolean onCreateOptionsMenu(Menu menu) {
     //   getMenuInflater().inflate(R.menu.menu, menu);
       // return super.onCreateOptionsMenu(menu);
    //}
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    public void init() {
         etchangeEmail = (EditText) findViewById(R.id.etchangeEmail);
        cardforget = (CardView) findViewById(R.id.cardforget);
        cardforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forget();
            }
        });


    }

    public void forget() {
        String etchangeemail = etchangeEmail.getText().toString();
        if (etchangeemail.isEmpty()) {
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_LONG).show();
            return;
        }
        firebaseAuth.sendPasswordResetEmail(etchangeemail)
                .addOnCompleteListener(this,new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //AppHeart.ClientID = task.getResult().getUser().getUid();
                            AppHeart.Toast(context, " Forgot Password Email sent");


                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                        }else {
                            AppHeart.Toast(context, "Please enter valid email address.");
                            return;
                        }
                    }
                });
    }
}
