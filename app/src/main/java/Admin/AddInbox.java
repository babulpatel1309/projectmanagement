package Admin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

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
 * Created by Babul Patel on 23/04/2017.
 */

public class AddInbox extends AdminBase {

    AddNotificationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.add_notification);

        getDb(dbRefInbox);
        init();
    }

    private void init() {

        setTitlebar("Add Inbox");
        binding.inboxLay.setVisibility(View.VISIBLE);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allData = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        binding.btnSubmitIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {

                    pd.show();
                    Map<String, String> value = new HashMap<String, String>();

                    value.put("title", AppHeart.getText(binding.etTitleIN));
                    value.put("content", AppHeart.getText(binding.etContentIN));
                    value.put("type", AppHeart.getText(binding.etTypeIN));
                    value.put("email", AppHeart.getText(binding.etEmailIN));
                    value.put("desc", AppHeart.getText(binding.etDescIN));

                    if (allData == null)
                        allData = new HashMap<String, HashMap<String, String>>();

                    dbRef.child("IN" + (allData.size())).setValue(value).addOnCompleteListener(AddInbox.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                AppHeart.Toast(context, "Added successfully.");
                                resetIT(new EditText[]{binding.etContentIN,binding.etContentIN,binding.etDescIN,binding.etEmailIN,binding.etTypeIN});
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
        if (AppHeart.getText(binding.etTitleIN).trim().length() <= 0) {
            AppHeart.Toast(context, "Please add title");
            return false;
        }

        if (AppHeart.getText(binding.etContentIN).trim().length() <= 0) {
            AppHeart.Toast(context, "Please add content");
            return false;
        }

        if (AppHeart.getText(binding.etDescIN).trim().length() <= 0) {
            AppHeart.Toast(context, "Please add Description");
            return false;
        }
        if (AppHeart.getText(binding.etEmailIN).trim().length() <= 0) {
            AppHeart.Toast(context, "Please add email");
            return false;
        }
        if (AppHeart.getText(binding.etTypeIN).trim().length() <= 0) {
            AppHeart.Toast(context, "Please add type");
            return false;
        }

        return true;
    }

    private void resetIT(EditText[] ets) {

        try {
            for (int i = 0; i < ets.length; i++) {
                ets[i].setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    HashMap<String, HashMap<String, String>> allData = new HashMap<>();

}
