package com.example.phase2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.entity.Item;
import com.example.phase2.entity.DemoUser;

import java.util.ArrayList;

public class DemoExpandWishToLendActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private InventoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DemoUser user = (DemoUser) MainActivity.getUser();
    private ArrayList<Item> itemList;
    private static Item currentClickedItem;
    private boolean inventoryClickedFromInfo = DemoMenuActivity.isInventoryClickedFromInfo();
    private boolean wishListClickedFromInfo = DemoMenuActivity.isWishLIstClickedFromInfo();
    private boolean willingToLendClickedFromInfo = DemoMenuActivity.isWillingToLendClickedFromInfo();

    public static Item getCurrentClickedItem() {
        return currentClickedItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_wish_to_lend);

        // set view name by activity
        TextView viewName = findViewById(R.id.name_of_expand);

        if (wishListClickedFromInfo) {
            itemList = user.getWillingToBorrowItems();
            String name = "WISH LIST";
            viewName.setText(name);
        }
        else if (willingToLendClickedFromInfo) {
            itemList = user.getWillingToLendItems();
            String name = "WILLING TO LEND";
            viewName.setText(name);
        }
        else {
            itemList = user.getInventory();
        }

        // the lines below are for testing
//        itemList.add(new Item("LINE 1", "LINE2", R.drawable.login_background));
//        itemList.add(new Item("LINE 3", "LINE4", R.drawable.sign_up_bg));
//        itemList.add(new Item("LINE 5", "LINE6", R.drawable.admin_menu_background));
//        itemList.add(new Item("LINE 7", "LINE8", R.drawable.admin_menu_background));
//        itemList.add(new Item("LINE 9", "LINE10", R.drawable.admin_menu_background));
//        itemList.add(new Item("LINE 11", "LINE12", R.drawable.admin_menu_background));
//        itemList.add(new Item("LINE 13", "LINE14", R.drawable.admin_menu_background));
        this.buildRecyclerView();

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.WashedPink));
        }


        // back to demo menu
        Button inventoryBackButton = findViewById(R.id.button_Inventory_Back);
        inventoryBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inventoryClickedFromInfo) {
                    DemoMenuActivity.setInventoryClickedFromInfo(false);
                    Intent intent = new Intent(DemoExpandWishToLendActivity.this, DemoAccountInfoActivity.class);
                    startActivity(intent);
                }
                else if (wishListClickedFromInfo) {
                    DemoMenuActivity.setWishLIstClickedFromInfo(false);
                    Intent intent = new Intent(DemoExpandWishToLendActivity.this, DemoAccountInfoActivity.class);
                    startActivity(intent);
                }
                else if (willingToLendClickedFromInfo) {
                    DemoMenuActivity.setWillingToLendClickedFromInfo(false);
                    Intent intent = new Intent(DemoExpandWishToLendActivity.this, DemoAccountInfoActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(DemoExpandWishToLendActivity.this, DemoMenuActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });
    }

    // build recyclerview
    public void buildRecyclerView () {

        mRecyclerView = findViewById(R.id.inventoryView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // pass in the lists of items
        mAdapter = new InventoryAdapter(itemList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new InventoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(DemoExpandWishToLendActivity.this, "This is a demo. " +
                        "Register and start trading now!", Toast.LENGTH_LONG).show();
            }
        });

    }
}