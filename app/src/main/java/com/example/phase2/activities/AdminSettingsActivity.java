package com.example.phase2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.phase2.R;

import java.util.Objects;

public class AdminSettingsActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.EnergyYellow));
        }

        Button logoutButton = findViewById(R.id.button_Admin_Log_Out);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(AdminSettingsActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.loading_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                Intent intent = new Intent(AdminSettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                // for push
            }
        });

        Button settingBackButton = findViewById(R.id.button_Admin_Setting_Back);
        settingBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSettingsActivity.this, AdminMenuActivity.class);
                startActivity(intent);
            }
        });

        Button changePassButton = findViewById(R.id.button_Admin_Change_Password);
        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSettingsActivity.this, AdChangePassActivity.class);
                startActivity(intent);
            }
        });


        Button changeThreshold = findViewById(R.id.button_Change_Threshold);
        changeThreshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSettingsActivity.this, ChangeThresholdActivity.class);
                startActivity(intent);
            }
        });


        Button addingAdmin = findViewById(R.id.button_Add_New_Admin);
        addingAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSettingsActivity.this, AdminUserAddingActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onDestroy() {
        progressDialog.dismiss();
        super.onDestroy();
    }
}