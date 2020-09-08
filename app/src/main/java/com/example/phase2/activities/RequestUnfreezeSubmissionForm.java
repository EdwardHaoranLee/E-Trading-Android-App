package com.example.phase2.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.gateway.UserData;

public class RequestUnfreezeSubmissionForm extends AppCompatActivity {
    private RegularUser user = (RegularUser) MainActivity.getUser();
    private UserData ud = MainActivity.getUserData();
    RegularUser regularUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_unfreeze_submission_form);

        Button cancleButton = findViewById(R.id.button_cancel_hhhh);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestUnfreezeSubmissionForm.this, RegularMenuActivity.class);
                startActivity(intent);
            }
        });

        EditText textRequest = findViewById(R.id.editText_Request_unfreeze_hhhh);



        Button submitButton = findViewById(R.id.button_submission_unfreeze_hhhh);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {
                user.setIsUnfreezeRequestSent(true);
                user.setUnfreezeRequest(textRequest.getText().toString());

                for(RegularUser ru : ud.getRegularUsers()){
                    if(ru.getUsername().equals(user.getUsername())){
                        regularUser = ru;
                    }
                }
                String text = textRequest.getText().toString();
                regularUser.setUnfreezeRequest(text);
                Toast.makeText(RequestUnfreezeSubmissionForm.this, "Your request is sent", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RequestUnfreezeSubmissionForm.this, RegularMenuActivity.class);
                startActivity(intent);

            }
        });
    }
}