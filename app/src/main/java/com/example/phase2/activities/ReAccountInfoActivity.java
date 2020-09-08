package com.example.phase2.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.phase2.R;
import com.example.phase2.entity.Item;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.presenter.UserInfoPresenter;

import java.util.ArrayList;


public class ReAccountInfoActivity extends AppCompatActivity {

    private RegularUser user = (RegularUser) MainActivity.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_account_info);


        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LightGreen));
        }

        // change the name of the page according to the user info
        TextView userInfo = findViewById(R.id.re_user_info);
        String name = user.getUsername();
        userInfo.setText(name);

        // inventory
        Button inventoryButton = findViewById(R.id.button_inventory);
        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegularMenuActivity.setInventoryClickedFromInfo(true);
                Intent intent = new Intent(ReAccountInfoActivity.this, ExpandWishToLendActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // wish list
        Button wishListButton = findViewById(R.id.button_Wish_List);
        wishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegularMenuActivity.setWishLIstClickedFromInfo(true);
                Intent intent = new Intent(ReAccountInfoActivity.this, ExpandWishToLendActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // willing to lend
        Button willingToLendButton = findViewById(R.id.button_Willing_Lend);
        willingToLendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegularMenuActivity.setWillingToLendClickedFromInfo(true);
                Intent intent = new Intent(ReAccountInfoActivity.this, ExpandWishToLendActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // send unfreeze request
        Button unfreezeButton = findViewById(R.id.button_Send_Unfreeze_Request);
        unfreezeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReAccountInfoActivity.this, SendUnfreezeRequestActivity.class);
                startActivity(intent);
                finish();
            }
        });



        // go backwards
        final Button reInfoButton = findViewById(R.id.button_Regular_Info_Back);
        reInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReAccountInfoActivity.this, RegularMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // show account status
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showPopUp(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.inflate(R.menu.account_status_menu);

        Menu menu = popup.getMenu();

        // set account status
        String accountStatus = "";
        String tradingStatus = "";
        if (user.getIsBusy() && !user.getIsFrozen()) {
            accountStatus = "Account Status: On Vacation";
            tradingStatus = "Trading Status: Normal";
        }
        else if (!user.getIsBusy() && !user.getIsFrozen()) {
            accountStatus = "Account Status: Normal";
            tradingStatus = "Trading Status: Normal";
        }
        else if (!user.getIsBusy() && user.getIsFrozen()) {
            accountStatus = "Account Status: Frozen";
            tradingStatus = "Trading Status: Suspicious";
        }
        else {
            accountStatus = "Account Status: On Vacation, Frozen";
            tradingStatus = "Trading Status: Suspicious";
        }

        // get most traded items and favorite partners
        UserInfoPresenter presenter = new UserInfoPresenter(user.getUsername());
        StringBuilder partners = new StringBuilder("Favorite Partners: ");
        StringBuilder items = new StringBuilder("Recent Traded Items: ");
        ArrayList<String> favorites = presenter.favoritePartners();
        for (String s: favorites){
            partners.append("\n").append(s);
        }
        ArrayList<Item> itemList = presenter.presentRecentTradingItems(3);
        for (Item i: itemList){
            items.append("\n").append(i.getName());
        }
        menu.getItem(0).setTitle(accountStatus);
        menu.getItem(1).setTitle(tradingStatus);
        menu.getItem(2).setTitle(partners);
        menu.getItem(3).setTitle(items);

        popup.show();
    }
}