package com.example.phase2.activities;

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
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.controller.UndoRegularUser;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.gateway.UserData;

import java.util.ArrayList;

public class UndoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private UndoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private UserData ud = MainActivity.getUserData();
    private ArrayList<RegularUser> userList = new ArrayList<>();
    private static RegularUser currentClickedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undo);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LemonYellow));
        }

        EditText theFilter = findViewById(R.id.undo_search_bar);
        userList = ud.getRegularUsers();

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
        Button undoBackButton = findViewById(R.id.button_undo_users_Back);
        undoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminMenuActivity.setUnfreezeClicked(false);
                Intent intent = new Intent(UndoActivity.this, AdminMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // build recyclerview
    public void buildRecyclerView() {

        mRecyclerView = findViewById(R.id.undoUserView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // pass in the lists of items
        mAdapter = new UndoAdapter(userList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new UndoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                currentClickedUser = userList.get(position);
//                Intent intent = new Intent(AdViewSuspiciousActivity.this, SuspiciousClickedActivity.class);
//                startActivity(intent);
            }

            @Override
            public void onUndoClick(int position) {
                currentClickedUser = userList.get(position);
                String message = UndoRegularUser.undo(ud, currentClickedUser);
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(UndoActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}