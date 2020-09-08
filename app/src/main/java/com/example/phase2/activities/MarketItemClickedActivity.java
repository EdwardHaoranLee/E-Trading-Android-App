package com.example.phase2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.entity.Item;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.use_case.RegularUserEditItems;

public class MarketItemClickedActivity extends AppCompatActivity {

    private Item currentItem;
    private RegularUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_item_clicked);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.WashedPink));

            currentItem = ViewMarketActivity.getCurrentClickedItem();
            user = (RegularUser) MainActivity.getUser();
            TextView title = findViewById(R.id.market_item_name);
            TextView description = findViewById(R.id.market_item_description);
            TextView username = findViewById(R.id.market_item_owner);
            ImageView image = findViewById(R.id.market_item_image);
            Button backButton = findViewById(R.id.button_market_item_Back);
            Button twoWayButton = findViewById(R.id.market_two_way_button);
            Button oneWayButton = findViewById(R.id.market_one_way_button);
            ImageButton addToWishButton = findViewById(R.id.market_item_addwishlist_button);

            // set item title
            title.setText(currentItem.getName());

            // set item description
            description.setText(currentItem.getDescription());

            // set item image
            image.setImageBitmap(currentItem.getImageResource());

            // set owner's name
            String name = "Owner: " + currentItem.getUsername();
            username.setText(name);

            // back button
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MarketItemClickedActivity.this, ViewMarketActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            // add to wish list
            addToWishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!user.getWillingToBorrowItems().contains(currentItem)) {
                        RegularUserEditItems editItems = new RegularUserEditItems(currentItem, user);
                        editItems.addToWishList();
                        Toast.makeText(MarketItemClickedActivity.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MarketItemClickedActivity.this, "The item is already in you Wish List", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // two way activity
            twoWayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(MarketItemClickedActivity.this, TwoWayTradeActivity.class);
                    startActivity(intent);
                }
            });

            // one way activity
            oneWayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(MarketItemClickedActivity.this, OneWayTradeActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}