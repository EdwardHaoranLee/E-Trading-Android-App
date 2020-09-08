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
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.UIs.UIManager;
import com.example.phase2.entity.Item;
import com.example.phase2.entity.DemoUser;
import com.example.phase2.entity.OneWayTrade;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.entity.Transaction;
import com.example.phase2.entity.TransactionRequest;
import com.example.phase2.entity.TwoWayTrade;
import com.example.phase2.gateway.UserData;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DemoConfirmTransactionActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TransactionsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DemoUser user = (DemoUser) MainActivity.getUser();
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

        this.buildRecyclerView();

        //initialize the transactions
        Item tofu = new Item("Mapo Tofu", "HP +10");
        Item book = new Item("The Three Representatives", "Mint condition");
        Item syrup = new Item("Maple syrup", "Made in Canada");
        ArrayList<Item> list1 = new ArrayList<>();

        list1.add(tofu);
        ArrayList<Item> list2 = new ArrayList<>();
        list2.add(book);
        list2.add(syrup);
        RegularUser user1 = new RegularUser("Han Meimei", "123");
        RegularUser user2 = new RegularUser("Jason", "123");
        RegularUser user3 = new RegularUser("demo", "demo");

        TransactionRequest request1 = new TransactionRequest(user1, user3,
                "Bay Station", LocalDateTime.now(), list1,
                false, true );
        TransactionRequest request2 = new TransactionRequest(user2, user3,
                "Toronto Island", LocalDateTime.now(), list2,
                true, true );

        transactionsList.add(new OneWayTrade(request1, LocalDateTime.now()));
        transactionsList.add(new TwoWayTrade(request2, LocalDateTime.now()));





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
                Intent intent = new Intent(DemoConfirmTransactionActivity.this, DemoMenuActivity.class);
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
        mAdapter = new TransactionsAdapter(transactionsList, DemoConfirmTransactionActivity.this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new TransactionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(DemoConfirmTransactionActivity.this, "This is a demo. " +
                        "Register and start trading now!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConfirmClick(int position) {
                Toast.makeText(DemoConfirmTransactionActivity.this, "This is a demo. " +
                        "Register and start trading now!", Toast.LENGTH_LONG).show();
            }
        });

    }
}