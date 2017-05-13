package login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.projectmanagement.AppHeart;
import com.projectmanagement.MainActivity;
import com.projectmanagement.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    LinearLayout btnSignIn, btnSignUp, boxSIGNUP, boxSIGNIN, lay_forgot;
    EditText etEmail, etPass, etname, etusername, etPassword, etcmpassword, etnumber;
    CardView signincard, signupcard;
    ViewFlipper flipper;
    Animation slide_in_left, slide_out_right;
    Animation slide_in_right, slide_out_left;
    TextView forgotpass;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    Context context = this;


    View lineSignIn, lineSignUp;

    DatabaseReference dbRef, dbRef_UserList;
    StorageReference stRef;

    CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        stRef = FirebaseStorage.getInstance().getReference();
        dbRef_UserList = FirebaseDatabase.getInstance().getReference("Notifications");

        progressDialog = new ProgressDialog(this);
        slide_in_left = AnimationUtils.loadAnimation(this,
                R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this,
                R.anim.slide_out_right);

        slide_in_right = AnimationUtils.loadAnimation(this,
                R.anim.slide_in_right);
        slide_out_left = AnimationUtils.loadAnimation(this,
                R.anim.slide_out_left);

        init();


    }


    private void init() {

        btnSignIn = (LinearLayout) findViewById(R.id.btnSignIn);
        btnSignUp = (LinearLayout) findViewById(R.id.btnSignUp);
        boxSIGNUP = (LinearLayout) findViewById(R.id.boxSIGNUP);
        boxSIGNIN = (LinearLayout) findViewById(R.id.boxSIGNIN);
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        lineSignIn = (View) findViewById(R.id.lineSignIn);
        lineSignUp = (View) findViewById(R.id.lineSignUp);
        signincard = (CardView) findViewById(R.id.signincard);
        signupcard = (CardView) findViewById(R.id.signupcard);
        lay_forgot = (LinearLayout) findViewById(R.id.lay_forgot);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        etname = (EditText) findViewById(R.id.name);
        etusername = (EditText) findViewById(R.id.etusername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etcmpassword = (EditText) findViewById(R.id.etcmpassword);
        etnumber = (EditText) findViewById(R.id.etnumber);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);

        flipper.setInAnimation(context, android.R.anim.slide_in_left);
        flipper.setOutAnimation(context, android.R.anim.slide_out_right);

        lineSignIn.startAnimation(slide_in_right);
        lineSignUp.startAnimation(slide_out_right);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flipper.setInAnimation(slide_in_left);
                flipper.setOutAnimation(slide_out_right);

                if (flipper.getDisplayedChild() == 1) {
                    flipper.showNext();
                    lineSignUp.setVisibility(View.GONE);
                    lineSignIn.setVisibility(View.VISIBLE);
                    lay_forgot.setVisibility(View.VISIBLE);
                }

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flipper.setInAnimation(slide_in_right);
                flipper.setOutAnimation(slide_out_left);

                if (flipper.getDisplayedChild() == 0) {
                    flipper.showPrevious();

                    lineSignUp.setVisibility(View.VISIBLE);
                    lineSignIn.setVisibility(View.GONE);
                    lay_forgot.setVisibility(View.GONE);
                }
            }
        });
        forgotpass = (TextView) findViewById(R.id.forgotpass);
        forgotpass.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(i);
            }
        });

        signincard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        signupcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1000);
    }

    private Uri filePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void login() {
        String etemail = etEmail.getText().toString();
        String etpass = etPass.getText().toString();

        if (etemail.isEmpty()) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (etpass.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Login Please Wait...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(etemail, etpass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            AppHeart.ClientID = task.getResult().getUser().getUid();
                            dbRef.child(AppHeart.ClientID).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    updateDeviceID();

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        } else {
                            checkIfEmailVerified();
                            Toast.makeText(context, "Email is not Verified.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
            if (user.isEmailVerified()) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                // user is verified, so you can finish this activity or send user to activity which you want.
                finish();
                Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
            } else {
                // email is not verified, so just prompt the message to the user and restart this activity.
                // NOTE: don't forget to log out the user.
                FirebaseAuth.getInstance().signOut();

                //restart this activity

            }
    }

    private void register() {
        final String name = etname.getText().toString();
        final String uesrname = etusername.getText().toString();
        final String pass = etPassword.getText().toString();
        String cmpass = etcmpassword.getText().toString();
        final String num = etnumber.getText().toString();

        if (name.isEmpty()) {
            displayToast("name field empty");
            return;
        }
        if (uesrname.isEmpty()) {
            displayToast("Username field empty");
            return;
        }
        if (pass.isEmpty()) {
            displayToast("password field empty");
            return;
        }
        if (cmpass.isEmpty() || num.isEmpty()) {
            displayToast("Confirm password field empty");
            return;
        }
        if (num.isEmpty()) {
            displayToast("Number field empty");
            return;
        }
        if (!pass.equals(cmpass)) {
            displayToast("Password don't match.");
            return;
        }
        if (num.trim().length() < 10) {
            displayToast("Number is invalid.");
            return;
        }

        if (filePath == null) {
            AppHeart.Toast(context, "Please choose profile picture.");
            return;
        }

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(uesrname, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    //display some message here
                    Map<String, String> userData = new HashMap<>();
                    userData.put("Name", name);
                    userData.put("Email", uesrname);
                    userData.put("Password", pass);
                    userData.put("Number", num);
                    userData.put("DeviceID", FirebaseInstanceId.getInstance().getToken());
                    userData.put("type", "1");//0 for admin ; 1 for developer  ; 2 for manager

                    AppHeart.ClientID = task.getResult().getUser().getUid();
                    addToDB(userData);

                    stRef.child("ProfilePIC/" + AppHeart.getFileExt(AppHeart.getFileName(context, filePath))).putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isComplete() && task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Not registered Successfully ", Toast.LENGTH_LONG).show();
                            }


                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });

                    firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            if (user != null) {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(LoginActivity.this, "Signup successful. Verification email sent", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    });

                } else {
                    //display some message here
                    Toast.makeText(LoginActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void addToDB(Map<String, String> userData) {
        dbRef.child(AppHeart.ClientID).setValue(userData);
    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateDeviceID() {
        dbRef.child(AppHeart.ClientID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("Email")) {
                    Map<String, String> data = (Map<String, String>) dataSnapshot.getValue();
                    data.put("DeviceID", FirebaseInstanceId.getInstance().getToken());
                    data.put("ClientID", AppHeart.ClientID);

                    if (dataSnapshot.hasChild("type")) {
                        AppHeart.UserType = data.get("type");
                    } else {
                        AppHeart.UserType = "1";
                    }

                    addToDB(data);
                }


                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
