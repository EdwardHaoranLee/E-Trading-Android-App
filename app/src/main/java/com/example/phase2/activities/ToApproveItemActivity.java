package com.example.phase2.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.phase2.controller.ApproveAddingItem;
import com.example.phase2.entity.Item;
import com.example.phase2.gateway.UserData;

public class ToApproveItemActivity extends AppCompatActivity {

    private Item currentItem;
    private static int currentPosition;
    private RecyclerView recyclerView1;
    private String username;
    public static final String EXTRA_POSITION =
            "com.example.phase2.EXTRA_POSITION";
    public static final String EXTRA_DATA_CHANGED =
            "com.example.phase2.EXTRA_DATA_CHANGED";
    UserData ud = MainActivity.getUserData();


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_approve_item);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LemonYellow));

            String grantUsername = EnterUserItemRequestActivity.getUsername();
            currentItem = EnterUserItemRequestActivity.getCurrentClickedItem();
            currentPosition = EnterUserItemRequestActivity.getCurrentPosition();
            recyclerView1 = EnterUserItemRequestActivity.getmRecyclerView();

            TextView title = findViewById(R.id.REQUEST_item_name);
            TextView description = findViewById(R.id.REQUEST_item_description);
            ImageView image = findViewById(R.id.REQUEST_item_image);
            Button approveButton = findViewById(R.id.ad_approve_item_button);
            Button disapproveButton = findViewById(R.id.ad_disapprove_item_button);
            Button backButton = findViewById(R.id.button_Approved_Back);

            // set item title
            title.setText(currentItem.getName());

            // set item description
            description.setText(currentItem.getDescription());

            // set item image
            image.setImageBitmap(currentItem.getImageResource());

            // approve the item
            approveButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.R)
                @Override
                public void onClick(View view) {
                    ApproveAddingItem approver = new ApproveAddingItem(true);
                    approver.changingItem(grantUsername, currentItem, ud);
                    Toast.makeText(ToApproveItemActivity.this, "Approved Successfully!", Toast.LENGTH_SHORT).show();
//                    Intent data = new Intent();
//                    data.putExtra(EXTRA_POSITION, currentPosition);
//                    data.putExtra(EXTRA_DATA_CHANGED, true);
//                    setResult(RESULT_OK, data);
//                    finish();
                    recyclerView1.getAdapter().notifyItemRemoved(currentPosition);
                    finish();
                    }
            });

            // disapprove the item
            disapproveButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.R)
                @Override
                public void onClick(View view) {
                    ApproveAddingItem approver = new ApproveAddingItem(false);
                    approver.changingItem(grantUsername, currentItem, ud);
                    Toast.makeText(ToApproveItemActivity.this, "Disapproved Successfully!", Toast.LENGTH_SHORT).show();
//                    Intent data = new Intent();
//                    data.putExtra(EXTRA_POSITION, currentPosition);
//                    data.putExtra(EXTRA_DATA_CHANGED, true);
//                    setResult(RESULT_OK, data);
//                    finish();
                    recyclerView1.getAdapter().notifyItemRemoved(currentPosition);
                    finish();
                }
            });

            // back to upper level
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ToApproveItemActivity.this, EnterUserItemRequestActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}