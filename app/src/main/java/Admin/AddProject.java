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
 * Created by Babul Patel on 22/04/2017.
 */

public class AddProject extends AdminBase {

    AddNotificationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.add_notification);

        getDb(dbRefProjects);

        init();
    }

    HashMap<String, HashMap<String, String>> allData = new HashMap<>();

    private void init() {

        setTitlebar("Add Projects");
        binding.projectsLay.setVisibility(View.VISIBLE);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allData = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        binding.btnSubmitPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {

                    pd.show();
                    Map<String, String> value = new HashMap<String, String>();

                    value.put("name", AppHeart.getText(binding.etNamePR));
                    value.put("desc", AppHeart.getText(binding.etDescPR));
                    value.put("no_member", AppHeart.getText(binding.etMembersPR));
                    value.put("name_member", AppHeart.getText(binding.etMemberNamePR));
                    value.put("status", binding.rbPeningPR.isChecked() ? "0" : "1");

                    if (allData == null)
                        allData = new HashMap<String, HashMap<String, String>>();

                    dbRef.child("PR" + (allData.size())).setValue(value).addOnCompleteListener(AddProject.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                AppHeart.Toast(context, "Added successfully.");
                                resetIT(new EditText[]{binding.etNamePR, binding.etDescPR, binding.etMemberNamePR, binding.etMembersPR});
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

        if (AppHeart.getText(binding.etNamePR).length() <= 0) {
            AppHeart.Toast(context, "Please enter name");
            return false;
        }
        if (AppHeart.getText(binding.etDescPR).length() <= 0) {
            AppHeart.Toast(context, "Please enter description");
            return false;
        }
        if (AppHeart.getText(binding.etMemberNamePR).length() <= 0) {
            AppHeart.Toast(context, "Please enter members name");
            return false;
        }
        if (AppHeart.getText(binding.etMembersPR).length() <= 0) {
            AppHeart.Toast(context, "Please enter no of members");
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


}
