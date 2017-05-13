package Admin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

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

public class AddTasks extends AdminBase {

    AddNotificationBinding binding;
    private String status = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.add_notification);

        getDb(dbRefTasks);

        init();
    }

    HashMap<String, HashMap<String, String>> allData = new HashMap<>();

    private void init() {

        setTitlebar("Add Tasks");
        binding.taskLay.setVisibility(View.VISIBLE);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allData = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        binding.rgStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if(checkedId==R.id.rbPendingTS){
                    status="0";
                }
                if(checkedId==R.id.rbRunningTS){
                    status="1";
                }
                if(checkedId==R.id.rbCompletedTS){
                    status="2";
                }

            }
        });

        binding.btnSubmitTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {

                    pd.show();
                    Map<String, String> value = new HashMap<String, String>();

                    value.put("title", AppHeart.getText(binding.etTitleTS));
                    value.put("desc", AppHeart.getText(binding.etDescTS));
                    value.put("assignedby", AppHeart.getText(binding.etAssignedByTS));
                    value.put("assignedto", AppHeart.getText(binding.etAssignedToTS));
                    value.put("status", status);

                    if (allData == null)
                        allData = new HashMap<String, HashMap<String, String>>();

                    dbRef.child("TS" + (allData.size())).setValue(value).addOnCompleteListener(AddTasks.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                AppHeart.Toast(context, "Added successfully.");
                                resetIT(new EditText[]{binding.etTitleTS, binding.etDescTS, binding.etAssignedByTS, binding.etAssignedToTS});
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

        if(AppHeart.getText(binding.etTitleTS).length()<=0){
            AppHeart.Toast(context,"Please enter title");
            return false;
        }
        if(AppHeart.getText(binding.etDescTS).length()<=0){
            AppHeart.Toast(context,"Please enter Description");
            return false;
        }
        if(AppHeart.getText(binding.etAssignedByTS).length()<=0){
            AppHeart.Toast(context,"Please enter Assigned By");
            return false;
        }
        if(AppHeart.getText(binding.etAssignedToTS).length()<=0){
            AppHeart.Toast(context,"Please enter Assigned To");
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
