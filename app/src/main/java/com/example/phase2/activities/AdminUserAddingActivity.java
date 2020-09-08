package com.example.phase2.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.phase2.R;
import com.example.phase2.entity.AdminUser;
import com.example.phase2.gateway.UserData;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminUserAddingActivity extends AppCompatActivity {
    private UserData userData = (UserData) MainActivity.getUserData();
    private ArrayList<AdminUser> adminUserArrayList = userData.getAdminUsers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_admin_user);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LemonYellow));
        }

        Button confirmAddingButton = findViewById(R.id.button_confirm_adding_todo);
        confirmAddingButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {
                EditText textUsername = findViewById(R.id.editTextTextPersonName_todo);
                String username = textUsername.getText().toString();
                EditText passwordText = findViewById(R.id.editTextTextPassword_todo);
                String password = passwordText.getText().toString();
                if(!(username.equals("")) && checkDuplicateAdmin(username)){

                    if(!(password.equals(""))){
                        AdminUser adminUser = new AdminUser(username, password);
                        Toast.makeText(AdminUserAddingActivity.this, "The new AdminUser with username " + username + " and password " + password + " is created successfully. ",
                                Toast.LENGTH_SHORT).show();
                        userData.addUser(adminUser);
                    }else{
                        Toast.makeText(AdminUserAddingActivity.this, "The password should not be null. Try Again",
                                Toast.LENGTH_SHORT).show();
                        resetToEmpty(textUsername);
                        resetToEmpty(passwordText);
                    }
                    //
                }else if(username.equals("")){
                    Toast.makeText(AdminUserAddingActivity.this, "The username should not be null. Try Again",
                            Toast.LENGTH_SHORT).show();
                    resetToEmpty(textUsername);
                    resetToEmpty(passwordText);
                }else{
                    Toast.makeText(AdminUserAddingActivity.this, "The username already exists. Try Again",
                            Toast.LENGTH_SHORT).show();
                    resetToEmpty(textUsername);
                    resetToEmpty(passwordText);
                }
            }
        });

        Button goBackButton = findViewById(R.id.button_add_admin_back);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminUserAddingActivity.this, AdminSettingsActivity.class);
                startActivity(intent);
            }
        });


    }

    private boolean checkDuplicateAdmin(String username){
        for(AdminUser user: adminUserArrayList){
            if(user.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

    private void resetToEmpty(EditText textEdit){
        textEdit.setText("");
    }
}