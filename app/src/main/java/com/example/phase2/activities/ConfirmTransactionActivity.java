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

import com.example.phase2.R;
import com.example.phase2.UIs.UIManager;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.entity.Transaction;
import com.example.phase2.gateway.UserData;

import java.util.ArrayList;

public class ConfirmTransactionActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TransactionsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RegularUser user = (RegularUser) MainActivity.getUser();
    private UserData ud = MainActivity.getUserData();
    private ArrayList<Transaction> transactionsList = new ArrayList<>();
    private Transaction selectedTransaction;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_transaction);

        EditText theFilter = findViewById(R.id.confirm_transaction_search_bar);

        UIManager uiManager = new UIManager(user, ud);
        transactionsList = uiManager.allTransactions();

        this.buildRecyclerView();

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.WashedPink));
        }

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

        // back button
        Button marketBackButton = findViewById(R.id.button_Confirm_Transaction_Back);
        marketBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmTransactionActivity.this, RegularMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // build recyclerview
    public void buildRecyclerView() {

        mRecyclerView = findViewById(R.id.confirmTransactionView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // pass in the lists of items
        mAdapter = new TransactionsAdapter(transactionsList, ConfirmTransactionActivity.this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new TransactionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                currentClickedItem = itemList.get(position);
//                Intent intent = new Intent(TwoWayChooseItemActivity.this, MarketItemClickedActivity.class);
//                startActivity(intent);
            }

            @Override
            public void onConfirmClick(int position) {
                selectedTransaction = transactionsList.get(position);
                selectedTransaction.setConfirmationNum(1);
                mAdapter.notifyItemChanged(position);
            }
        });

    }
}