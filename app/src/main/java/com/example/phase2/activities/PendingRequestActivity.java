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
import com.example.phase2.entity.RegularUser;
import com.example.phase2.entity.TransactionRequest;

import java.util.ArrayList;

public class PendingRequestActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PendingRequestAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RegularUser user = (RegularUser) MainActivity.getUser();
    private ArrayList<TransactionRequest> requestsList;
    private static TransactionRequest currentClickedRequest;

    public static TransactionRequest getCurrentClickedRequest() {
        return currentClickedRequest;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request);

        requestsList = user.getTransactionRequests();
        this.buildRecyclerView();

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.WashedPink));
        }


        // back to regular menu
        Button inventoryBackButton = findViewById(R.id.button_Pending_Request_Back);
        inventoryBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PendingRequestActivity.this, RegularMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // build recyclerview
    public void buildRecyclerView () {

        mRecyclerView = findViewById(R.id.pendingView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // pass in the lists of requests
        mAdapter = new PendingRequestAdapter(requestsList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new PendingRequestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                currentClickedRequest = requestsList.get(position);
                boolean isTwoWay = currentClickedRequest.getIsTwoWay();

                if (isTwoWay) {
                    Intent intent = new Intent(PendingRequestActivity.this, RegularEditRequestTwoWayActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(PendingRequestActivity.this, RegularEditRequestOneWayActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onDenyClick(int position) {
                currentClickedRequest = requestsList.get(position);
                mAdapter.notifyItemRemoved(position);
                user.getTransactionRequests().remove(currentClickedRequest);
            }
        });

    }

    @Override
    protected void onResume() {
        requestsList = user.getTransactionRequests();
        this.buildRecyclerView();
        super.onResume();
    }
}