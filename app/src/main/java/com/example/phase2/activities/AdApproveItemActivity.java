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

import com.example.phase2.R;
import com.example.phase2.entity.AdminUser;
import com.example.phase2.entity.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AdApproveItemActivity extends AppCompatActivity {

    private AdminUser currentUser = (AdminUser) MainActivity.getUser();
    private static RecyclerView mRecyclerView;
    private ApproveItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private HashMap<String, ArrayList<Item>> userRequestMap;
    public static final int CLICK_USER_REQUEST = 1;
    private static final String TAG = "Group0116_Message:";

    private static ArrayList<Item> currentClickedKey;
    private static String currentKeyValue;

    public static ArrayList<Item> getCurrentClickedKey() {
        return currentClickedKey;
    }
    public static String getCurrentKeyValue() {return currentKeyValue;}
    public static RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_approve_item);

        // should discuss how to get data
        userRequestMap = currentUser.getItemRequests();
        this.buildRecyclerView();

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LemonYellow));
        }

        // go back to admin menu
        Button backButton = findViewById(R.id.button_Approve_Item_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdApproveItemActivity.this, AdminMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // build recyclerview
    public void buildRecyclerView () {

        mRecyclerView = findViewById(R.id.ItemRequestView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // pass in the lists of items
        mAdapter = new ApproveItemAdapter(userRequestMap);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemRequestClickListener(new ApproveItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                int currentKey = -1;

                // Getting an iterator
                Iterator<Map.Entry<String, ArrayList<Item>>> userIterator = userRequestMap.entrySet().iterator();
                while (userIterator.hasNext()) {
                    Map.Entry<String, ArrayList<Item>> mapElement = userIterator.next();
                    currentKey += 1;
                    if (currentKey == position) {
                        currentClickedKey = mapElement.getValue();
                        currentKeyValue = mapElement.getKey();
                        Intent intent = new Intent(AdApproveItemActivity.this, EnterUserItemRequestActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CLICK_USER_REQUEST && resultCode == RESULT_OK) {
//            int position = data.getIntExtra(ToApproveItemActivity.EXTRA_POSITION, 0);
//            boolean data_changed = data.getBooleanExtra(ToApproveItemActivity.EXTRA_DATA_CHANGED, false);
//
//            if (data_changed) {
//                mAdapter.notifyItemChanged(position);
//            }
//        }
//
//        Log.i(TAG, "onActivityResult");
//    }

}