package com.example.phase2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.phase2.R;
import com.example.phase2.entity.AdminUser;

public class AdminMenuActivity extends AppCompatActivity {

    private AdminUser user = (AdminUser) MainActivity.getUser();
    private static boolean unfreezeClicked = false;

    public static boolean isUnfreezeClicked() {
        return unfreezeClicked;
    }

    public static void setUnfreezeClicked(boolean unfreezeClicked) {
        AdminMenuActivity.unfreezeClicked = unfreezeClicked;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.EnergyYellow));
        }

        Button settingsButton = findViewById(R.id.button_Admin_Settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenuActivity.this, AdminSettingsActivity.class);
                startActivity(intent);
            }
        });

        Button approveItemButton = findViewById(R.id.button_Approve_Requests);
        approveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenuActivity.this, AdApproveItemActivity.class);
                startActivity(intent);
            }
        });

        Button suspiciousUserButton = findViewById(R.id.button_View_Suspicious);
        suspiciousUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenuActivity.this, AdViewSuspiciousActivity.class);
                startActivity(intent);
            }
        });

        Button freezeUnfreezeButton = findViewById(R.id.button_Freeze_Unfreeze);
        freezeUnfreezeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfreezeClicked = true;
                Intent intent = new Intent(AdminMenuActivity.this, AdViewSuspiciousActivity.class);
                startActivity(intent);
            }
        });

        Button undoButton = findViewById(R.id.button_Undo);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenuActivity.this, UndoActivity.class);
                startActivity(intent);
            }
        });


    }
}