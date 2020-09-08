package com.example.phase2.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.database.SaveData;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.use_case.RegularUserActionStack;

import java.util.Objects;

public class RegularSettingsActivity extends AppCompatActivity {

    private RegularUser user = (RegularUser) MainActivity.getUser();
    private ProgressDialog progressDialog;

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
                progressDialog = new ProgressDialog(RegularSettingsActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.loading_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                SaveData savingData = new SaveData();
                savingData.saveData(user);
                savingData.saveData((RegularUserActionStack) MainActivity.getUserData().getUndoStack(user.getUsername()));
                MainActivity.getUserData().saveAll();
                Intent intent = new Intent(RegularSettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button settingBackButton = findViewById(R.id.button_Regular_Setting_Back);
        settingBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegularSettingsActivity.this, RegularMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button changePassButton = findViewById(R.id.button_Change_Password);
        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegularSettingsActivity.this, ReChangePassActivity.class);
                startActivity(intent);
            }
        });

        Button setStatusButton = findViewById(R.id.button_Setting_Status);

        setStatusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegularSettingsActivity.this, VacationModeActivity.class);
                startActivity(intent);
            }
        });

        // Hiding buttons when freezing
//        hideButton(user.getIsFrozen(), settingsButton, "FROZEN");
//        hideButton(user.getIsFrozen(), setStatusButton, "FROZEN");
//        hideButton(user.getIsFrozen(), pendingButton, "FROZEN");
//        hideButton(user.getIsFrozen(), requestButton, "FROZEN");
//        hideButton(user.getIsFrozen(), expandButton, "FROZEN");
//        hideButton(user.getIsFrozen(), infoButton, "FROZEN");
//
//        // Hideing buttons when isBusy
//        hideButton(user.getiIsBusy(), viewMarketButton, "BUSY");
//        hideButton(user.getiIsBusy(), expandButton, "BUSY");

    }

//    @SuppressLint("SetTextI18n")
//    private void showBusyMessage(boolean isBusy){
//        TextView isBusyText = findViewById(R.id.text_busy_status);
//        if(isBusy){
//            isBusyText.setText("Your trading status: BUSY");
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    private void showFrozenMessage(boolean isFrozen){
//        TextView isFrozenText = findViewById(R.id.text_freezing_status);
//        if(isFrozen){
//            isFrozenText.setText("Your account status: Freezing");
//        }
//    }

    private void hideButton(boolean shouldHide, Button button, String status){
        if(shouldHide){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(RegularSettingsActivity.this, "You are currently blocked to this button since you are " + status,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }else{
//            button.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        progressDialog.dismiss();
        super.onDestroy();
    }
}
