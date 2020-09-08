package com.example.phase2.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.entity.RegularUser;

public class RegularMenuActivity extends AppCompatActivity {

    private RegularUser user = (RegularUser) MainActivity.getUser();
    private static boolean inventoryClickedFromInfo = false;
    private static boolean wishLIstClickedFromInfo = false;
    private static boolean willingToLendClickedFromInfo = false;

    public static boolean isInventoryClickedFromInfo() {
        return inventoryClickedFromInfo;
    }

    public static void setInventoryClickedFromInfo(boolean inventoryClickedFromInfo) {
        RegularMenuActivity.inventoryClickedFromInfo = inventoryClickedFromInfo;
    }

    public static boolean isWishLIstClickedFromInfo() {
        return wishLIstClickedFromInfo;
    }

    public static boolean isWillingToLendClickedFromInfo() {
        return willingToLendClickedFromInfo;
    }

    public static void setWishLIstClickedFromInfo(boolean wishLIstClickedFromInfo) {
        RegularMenuActivity.wishLIstClickedFromInfo = wishLIstClickedFromInfo;
    }

    public static void setWillingToLendClickedFromInfo(boolean willingToLendClickedFromInfo) {
        RegularMenuActivity.willingToLendClickedFromInfo = willingToLendClickedFromInfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_menu);


        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LightGreen));
        }

        if(user.getIsFrozen()){
            showUnfreezeScreen();
        }

        Button settingsButton = findViewById(R.id.button_Regular_Settings);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegularMenuActivity.this, RegularSettingsActivity.class);
                startActivity(intent);
            }
        });

        Button infoButton = findViewById(R.id.button_Regular_Account_Info);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegularMenuActivity.this, ReAccountInfoActivity.class);
                startActivity(intent);
            }
        });

        // button to open 'Expand Wish To Lend'
        Button expandButton = findViewById(R.id.button_Expand);

        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegularMenuActivity.this, ExpandWishToLendActivity.class);
                startActivity(intent);
            }
        });

        // button to open 'Request Adding Items'
        Button requestButton = findViewById(R.id.button_Request_Adding);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getIsFrozen()) {
                    Toast.makeText(RegularMenuActivity.this, "Your Account Is Frozen Now", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(RegularMenuActivity.this, RequestAddingItemActivity.class);
                    startActivity(intent);
                }
            }
        });

        // button to open 'Confirm Transaction'
        Button confirmButton = findViewById(R.id.button_Confirm_Transaction);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getIsFrozen()) {
                    Toast.makeText(RegularMenuActivity.this, "Your Account Is Frozen Now", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(RegularMenuActivity.this, ConfirmTransactionActivity.class);
                    startActivity(intent);
                }
            }
        });

        // button to open 'Pending Request'
        Button pendingButton = findViewById(R.id.button_Pending_Request);

        pendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getIsFrozen()) {
                    Toast.makeText(RegularMenuActivity.this, "Your Account Is Frozen Now", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(RegularMenuActivity.this, PendingRequestActivity.class);
                    startActivity(intent);
                }
            }
        });


        Button viewMarketButton = findViewById(R.id.button_View_Market);
        viewMarketButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegularMenuActivity.this, ViewMarketActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showUnfreezeScreen(){
        new AlertDialog.Builder(RegularMenuActivity.this).setIcon(R.drawable.warning_icon).setTitle("WARNING: You Are Frozen")
                .setPositiveButton("OK, I want to request for unfreeze", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(RegularMenuActivity.this, SendUnfreezeRequestActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No, go to the menu please", null).create().show();
    }
}