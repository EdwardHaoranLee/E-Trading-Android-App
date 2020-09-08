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

import com.example.phase2.R;
import com.example.phase2.entity.AdminUser;
import com.example.phase2.entity.Item;

import java.util.ArrayList;

public class EnterUserItemRequestActivity extends AppCompatActivity {

    private static RecyclerView mRecyclerView;
    private ClickUserAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdminUser user = (AdminUser) MainActivity.getUser();
    private ArrayList<Item> itemList;
    public static final int CLICK_ITEM_REQUEST = 1;
    private static final String TAG = "Group0116_Message:";


    private static String username;
    private static Item currentClickedItem;
    private static int currentPosition;

    public static Item getCurrentClickedItem() { return currentClickedItem; }

    public static int getCurrentPosition() {
        return currentPosition;
    }

    public static RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public static String getUsername() {
        return username;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_user_item_request);

//        itemList = AdApproveItemActivity.getCurrentClickedKey();
        username = AdApproveItemActivity.getCurrentKeyValue();
        itemList = user.getItemRequests().get(username);
        this.buildRecyclerView();

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LemonYellow));
        }

        // back to users list
        Button enterUserBack = findViewById(R.id.button_Enter_User_Back);
        enterUserBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnterUserItemRequestActivity.this, AdApproveItemActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // build recyclerview
    public void buildRecyclerView () {

        mRecyclerView = findViewById(R.id.UserRequestsView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // pass in the lists of items
        mAdapter = new ClickUserAdapter(itemList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        TextView name = findViewById(R.id.enter_Username);
        name.setText(username);

        mAdapter.setOnItemClickListener(new ClickUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                currentClickedItem = itemList.get(position);
                currentPosition = position;
                Intent intent = new Intent(EnterUserItemRequestActivity.this, ToApproveItemActivity.class);
                startActivity(intent);
                // finish()?
            }
        });

    }

    @Override
    protected void onResume() {
        username = AdApproveItemActivity.getCurrentKeyValue();
        itemList = user.getItemRequests().get(username);
        this.buildRecyclerView();
        super.onResume();
    }


    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CLICK_ITEM_REQUEST && resultCode == RESULT_OK) {
//            int position = data.getIntExtra(ToApproveItemActivity.EXTRA_POSITION, 0);
//            boolean data_changed = data.getBooleanExtra(ToApproveItemActivity.EXTRA_DATA_CHANGED, false);
//
//            if (data_changed) {
//                mAdapter.notifyItemRemoved(position);
//            }
//        }
//
//        Log.i(TAG, "onActivityResult");
//    }

}
