package com.example.phase2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phase2.R;
import com.example.phase2.entity.Item;

public class EditRequestTwoItemDetailActivity extends AppCompatActivity {

    private Item currentItem;
    private boolean firstItemCLicked;
    private boolean wishListClicked = RegularMenuActivity.isWishLIstClickedFromInfo();
    private boolean willingClicked = RegularMenuActivity.isWillingToLendClickedFromInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_request_two_item_detail);

        ImageView itemImage = findViewById(R.id.item_detail_two_image);
        TextView itemName = findViewById(R.id.item_detail_two_name);
        TextView itemDescription = findViewById(R.id.item_detail_two_description);
        firstItemCLicked = RegularEditRequestTwoWayActivity.isFirstImageClicked();

        if (firstItemCLicked) {
            currentItem = RegularEditRequestTwoWayActivity.getItem1();
        }
        else if (wishListClicked || willingClicked) {
            currentItem = ExpandWishToLendActivity.getCurrentClickedItem();
        }
        else {
            currentItem = RegularEditRequestTwoWayActivity.getItem2();
        }
        itemImage.setImageBitmap(currentItem.getImageResource());
        itemName.setText(currentItem.getName());
        itemDescription.setText(currentItem.getDescription());

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.WashedPink));
        }

        // back button
        Button backButton = findViewById(R.id.button_Two_Way_Detail_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wishListClicked || willingClicked) {
                    Intent intent = new Intent(EditRequestTwoItemDetailActivity.this, ExpandWishToLendActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(EditRequestTwoItemDetailActivity.this, RegularEditRequestTwoWayActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}