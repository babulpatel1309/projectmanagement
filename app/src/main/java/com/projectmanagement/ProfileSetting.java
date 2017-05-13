package com.projectmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Babul Patel on 18/04/2017.
 */

public class ProfileSetting extends AppCompatActivity {
    EditText changename, changeusername, changeemail, changenumber;
    Button savebutton;
    Context context = this;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbRef;
    ImageView profile_image;
    StorageReference stRef;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilesetting);

        changename = (EditText) findViewById(R.id.changename);
        changeusername = (EditText) findViewById(R.id.changeusername);
        changeemail = (EditText) findViewById(R.id.changeemail);
        changenumber = (EditText) findViewById(R.id.changenumber);
        savebutton = (Button) findViewById(R.id.savebutton);
        profile_image = (ImageView) findViewById(R.id.profile_image);

        firebaseAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile Settings");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        stRef = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);


        init();


        getData();
    }

    HashMap<String,String> userDetail = new HashMap<>();

    private void getData() {

        dbRef.child(AppHeart.ClientID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("Email")) {
                    try {
                        userDetail = (HashMap<String, String>) dataSnapshot.getValue();
                        changename.setText(userDetail.get("Name"));
                        changeemail.setText(userDetail.get("Email"));
                        changeusername.setText(userDetail.get("Email"));
                        changenumber.setText(userDetail.get("Number"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       String dp = stRef.child("ProfilePIC/" + AppHeart.ClientID).getDownloadUrl().toString();

        final long ONE_MEGABYTE = 1024 * 1024;
        stRef.child("ProfilePIC/" + AppHeart.ClientID + ".jpg").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed

                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                profile_image.setImageBitmap(Bitmap.createScaledBitmap(bmp, profile_image.getWidth(),
                        profile_image.getHeight(), false));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors

                exception.printStackTrace();
            }
        });

    }

    private void addToDB(Map<String, String> userData) {
        dbRef.child(AppHeart.ClientID).setValue(userData);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);

    }

    public void init() {
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                String etname = changename.getText().toString();
                String etusername = changeusername.getText().toString();
                String etemail = changeemail.getText().toString();
                String etnumber = changenumber.getText().toString();

                if (etname.isEmpty()) {
                    Toast.makeText(ProfileSetting.this, "Please enter name", Toast.LENGTH_LONG).show();
                    return;
                }

                if (etusername.isEmpty()) {
                    Toast.makeText(ProfileSetting.this, "Please enter username", Toast.LENGTH_LONG).show();
                    return;
                }
                if (etemail.isEmpty()) {
                    Toast.makeText(ProfileSetting.this, "Please enter email", Toast.LENGTH_LONG).show();
                    return;
                }

                if (etnumber.isEmpty()) {
                    Toast.makeText(ProfileSetting.this, "Please enter number", Toast.LENGTH_LONG).show();
                    return;
                }

                Map<String, String> userData = new HashMap<>();
                userData.put("Name", AppHeart.getText(changename));
                userData.put("Email", AppHeart.getText(changeemail));
                userData.put("Password", userDetail.get("Password"));
                userData.put("Number", AppHeart.getText(changenumber));

                addToDB(userData);

                if (filePath != null) {

                    stRef.child("ProfilePIC/" + AppHeart.ClientID + AppHeart.getFileExt(AppHeart.getFileName(context, filePath))).putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isComplete() && task.isSuccessful()) {
                                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Not updated Successfully ", Toast.LENGTH_LONG).show();
                            }

                            progressDialog.dismiss();

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
                }
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();
            }
        });
    }


    public void saveprofile() {
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();

                    // Check if user's email is verified
                    // boolean emailVerified = user.isEmailVerified();

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    String uid = user.getUid();
                }
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

}
