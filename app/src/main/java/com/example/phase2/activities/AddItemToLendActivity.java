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
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.entity.Item;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.use_case.RegularUserEditItems;

public class AddItemToLendActivity extends AppCompatActivity {

    private Item currentItem;
    private RegularUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_to_lend);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.WashedPink));

            currentItem = ExpandWishToLendActivity.getCurrentClickedItem();
            user = (RegularUser) MainActivity.getUser();
            TextView title = findViewById(R.id.item_name);
            TextView description = findViewById(R.id.item_description);
            ImageView image = findViewById(R.id.item_image);
            Button addButton = findViewById(R.id.re_add_to_lend_button);
            Button backButton = findViewById(R.id.button_Add_Item_Back);

            // set item title
            title.setText(currentItem.getName());

            // set item description
            description.setText(currentItem.getDescription());

            // set item image
            image.setImageBitmap(currentItem.getImageResource());


            // add to wish to lend
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!user.getWillingToLendItems().contains(currentItem)) {
                        RegularUserEditItems editItems = new RegularUserEditItems(currentItem, user);
                        editItems.addToAvailableList();
                        Toast.makeText(AddItemToLendActivity.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddItemToLendActivity.this, "The item is already in you WishToLend List", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AddItemToLendActivity.this, ExpandWishToLendActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}