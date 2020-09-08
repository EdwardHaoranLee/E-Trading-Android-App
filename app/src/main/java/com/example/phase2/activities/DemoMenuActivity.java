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
import com.example.phase2.entity.DemoUser;

public class DemoMenuActivity extends AppCompatActivity {

    private DemoUser user = (DemoUser) MainActivity.getUser();

    private static boolean inventoryClickedFromInfo = false;
    private static boolean wishLIstClickedFromInfo = false;
    private static boolean willingToLendClickedFromInfo = false;

    public static boolean isInventoryClickedFromInfo() {
        return inventoryClickedFromInfo;
    }

    public static void setInventoryClickedFromInfo(boolean inventoryClickedFromInfo) {
        DemoMenuActivity.inventoryClickedFromInfo = inventoryClickedFromInfo;
    }

    public static boolean isWishLIstClickedFromInfo() {
        return wishLIstClickedFromInfo;
    }

    public static boolean isWillingToLendClickedFromInfo() {
        return willingToLendClickedFromInfo;
    }

    public static void setWishLIstClickedFromInfo(boolean wishLIstClickedFromInfo) {
        DemoMenuActivity.wishLIstClickedFromInfo = wishLIstClickedFromInfo;
    }

    public static void setWillingToLendClickedFromInfo(boolean willingToLendClickedFromInfo) {
        DemoMenuActivity.willingToLendClickedFromInfo = willingToLendClickedFromInfo;
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

        Button settingsButton = findViewById(R.id.button_Regular_Settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoMenuActivity.this, DemoSettingsActivity.class);
                startActivity(intent);
            }
        });

        Button infoButton = findViewById(R.id.button_Regular_Account_Info);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoMenuActivity.this, DemoAccountInfoActivity.class);
                startActivity(intent);
            }
        });

        // button to open 'Expand Wish To Lend'
        Button expandButton = findViewById(R.id.button_Expand);
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoMenuActivity.this, DemoExpandWishToLendActivity.class);
                startActivity(intent);
            }
        });

        // button to open 'Request Adding Items'
        Button requestButton = findViewById(R.id.button_Request_Adding);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoMenuActivity.this, DemoRequestAddingItemActivity.class);
                startActivity(intent);
            }
        });

        // button to open 'Pending Request'
        Button pendingButton = findViewById(R.id.button_Pending_Request);
        pendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoMenuActivity.this, DemoPendingRequestActivity.class);
                startActivity(intent);
            }
        });


        Button viewMarketButton = findViewById(R.id.button_View_Market);
        viewMarketButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoMenuActivity.this, DemoViewMarketActivity.class);
                startActivity(intent);

            }
        });

        // button to open 'Confirm Transaction'
        Button confirmButton = findViewById(R.id.button_Confirm_Transaction);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoMenuActivity.this, DemoConfirmTransactionActivity.class);
                startActivity(intent);
                }

        });

    }
}