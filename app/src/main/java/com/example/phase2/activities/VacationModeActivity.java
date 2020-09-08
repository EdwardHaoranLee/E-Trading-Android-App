package com.example.phase2.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase2.R;
import com.example.phase2.entity.RegularUser;

public class VacationModeActivity extends AppCompatActivity {
    private RegularUser regularUser = (RegularUser) MainActivity.getUser();

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LightGreen));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_mode);
        Switch checkBusy = findViewById(R.id.userBusy);
        checkBusy.setTextSize(25);
        if(regularUser.getIsBusy()){
            checkBusy.setTextOn("On");
            checkBusy.setChecked(true);
        }else{
            checkBusy.setTextOff("Off");
            checkBusy.setChecked(false);
        }

        checkBusy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked && !regularUser.getIsBusy()){
                    regularUser.setIsBusy(true);
                    checkBusy.setChecked(true);
                }else{
                    regularUser.setIsBusy(false);
                    checkBusy.setChecked(false);
                }
            }
        });

        Button goBack = findViewById(R.id.button_Go_Back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationModeActivity.this, RegularSettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}