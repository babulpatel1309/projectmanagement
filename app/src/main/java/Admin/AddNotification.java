package Admin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.projectmanagement.AppHeart;
import com.projectmanagement.R;
import com.projectmanagement.databinding.AddNotificationBinding;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Babul Patel on 22/04/2017.
 */

public class AddNotification extends AdminBase {

    AddNotificationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.add_notification);

        getDb(dbRefNotification);
        init();
    }

    HashMap<String, HashMap<String, String>> allData = new HashMap<>();

    private void init() {

        setTitlebar("Add Notification");
        binding.nofiticationLay.setVisibility(View.VISIBLE);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allData = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {

                    pd.show();
                    Map<String, String> value = new HashMap<String, String>();

                    value.put("title", AppHeart.getText(binding.etTitle));
                    value.put("read", binding.chkRead.isChecked() ? "0" : "1");
                    value.put("desc", AppHeart.getText(binding.etDesc));
                    value.put("content", AppHeart.getText(binding.etContent));

                    if (allData == null)
                        allData = new HashMap<String, HashMap<String, String>>();

                    dbRef.child("note" + (allData.size())).setValue(value).addOnCompleteListener(AddNotification.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                AppHeart.Toast(context, "Added successfully.");
                                resetIT();
                            } else {
                                AppHeart.Toast(context, "Operation failed.");
                            }
                            pd.dismiss();
                        }
                    });
                }
            }
        });
    }


    private boolean validate() {

        if (AppHeart.getText(binding.etTitle).trim().length() <= 0) {
            AppHeart.Toast(context, "Please add title");
            return false;
        }

        if (AppHeart.getText(binding.etDesc).trim().length() <= 0) {
            AppHeart.Toast(context, "Please add description");
            return false;
        }

        if (AppHeart.getText(binding.etContent).trim().length() <= 0) {
            AppHeart.Toast(context, "Please add content");
            return false;
        }

        return true;
    }

    public void resetIT() {

        binding.etTitle.setText("");
        binding.etContent.setText("");
        binding.etDesc.setText("");
        binding.chkRead.setChecked(false);
    }

}
