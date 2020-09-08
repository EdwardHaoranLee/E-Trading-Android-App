package com.example.phase2.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.entity.DemoUser;

public class DemoSettingsActivity extends AppCompatActivity {

    private DemoUser user = (DemoUser) MainActivity.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_settings);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LightGreen));
        }

        Button logoutButton = findViewById(R.id.button_Log_Out);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoSettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button settingBackButton = findViewById(R.id.button_Regular_Setting_Back);
        settingBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoSettingsActivity.this, DemoMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button changePassButton = findViewById(R.id.button_Change_Password);
        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DemoSettingsActivity.this, "This is a demo. " +
                        "Register and start trading now!", Toast.LENGTH_LONG).show();
            }
        });

        Button setStatusButton = findViewById(R.id.button_Setting_Status);

        setStatusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(DemoSettingsActivity.this, "This is a demo. " +
                        "Register and start trading now!", Toast.LENGTH_LONG).show();
            }
        });

    }

}
