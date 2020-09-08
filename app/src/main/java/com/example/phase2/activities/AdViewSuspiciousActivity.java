package com.example.phase2.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.phase2.R;
import com.example.phase2.entity.AdminUser;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.gateway.UserData;
import com.example.phase2.use_case.FreezingAccount;

import java.util.ArrayList;

public class AdViewSuspiciousActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private UsersAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdminUser user = (AdminUser) MainActivity.getUser();
    private UserData ud = MainActivity.getUserData();
    private ArrayList<RegularUser> userList = new ArrayList<>();
    private static RegularUser currentClickedUser;
    private boolean unfreezeClicked = AdminMenuActivity.isUnfreezeClicked();

    public static RegularUser getCurrentClickedUser() {
        return currentClickedUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_view_suspicious);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LemonYellow));
        }

        EditText theFilter = findViewById(R.id.suspicious_search_bar);
        TextView title = findViewById(R.id.suspicious_textView);
        if (unfreezeClicked) {
            String unfreeze = "UNFREEZE USERS";
            title.setText(unfreeze);
            userList = ud.getFrozenUsers();
        }
        else {
            String suspicious = "SUSPICIOUS USERS";
            title.setText(suspicious);
            userList = ud.getSuspiciousUsers();
        }

        this.buildRecyclerView();


        // real-time search
        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        // back to admin menu
        Button marketBackButton = findViewById(R.id.button_suspicious_users_Back);
        marketBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminMenuActivity.setUnfreezeClicked(false);
                Intent intent = new Intent(AdViewSuspiciousActivity.this, AdminMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // build recyclerview
    public void buildRecyclerView() {

        mRecyclerView = findViewById(R.id.suspiciousUserView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // pass in the lists of items
        mAdapter = new UsersAdapter(userList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                currentClickedUser = userList.get(position);
//                Intent intent = new Intent(AdViewSuspiciousActivity.this, SuspiciousClickedActivity.class);
//                startActivity(intent);
            }

            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onFreezeClick(int position) {
                if (unfreezeClicked) {
                    currentClickedUser = userList.get(position);
                    RegularUser user = (RegularUser) ud.findUser(currentClickedUser.getUsername());
                    user.setFrozen(false);
                    userList.remove(currentClickedUser);
                    mAdapter.notifyItemRemoved(position);
                }
                else {
                    currentClickedUser = userList.get(position);
                    FreezingAccount freezer = new FreezingAccount();
                    freezer.freezeOperation(true, (RegularUser) currentClickedUser);
                    userList.remove(currentClickedUser);
                    mAdapter.notifyItemRemoved(position);
                }
                userList.remove(currentClickedUser);
            }
        });

    }
}