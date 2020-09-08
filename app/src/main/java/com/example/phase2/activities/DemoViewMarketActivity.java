package com.example.phase2.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//https://stackoverflow.com/questions/14779688/put-buttons-at-bottom-of-screen-with-linearlayout
import com.example.phase2.R;
import com.example.phase2.entity.Item;
import com.example.phase2.entity.DemoUser;
import com.example.phase2.gateway.UserData;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DemoViewMarketActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MarketAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DemoUser user = (DemoUser) MainActivity.getUser();
    private UserData ud = MainActivity.getUserData();
    private ArrayList<Item> itemList = new ArrayList<>();
    private static Item currentClickedItem;

    public static Item getCurrentClickedItem() {
        return currentClickedItem;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_market);

        EditText theFilter = findViewById(R.id.market_search_bar);

        itemList.add(new Item("Master Ball", "It catches everything", "Professor Oak"));
        itemList.add(new Item("Pikachu", "Lv 99", "Ash"));
        itemList.add(new Item("Exodia the Forbidden One", "The left hand", "Yugi"));


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


        // back to demo menu
        Button marketBackButton = findViewById(R.id.button_Market_Back);
        marketBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoViewMarketActivity.this, DemoMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    // build recyclerview
    public void buildRecyclerView() {

        mRecyclerView = findViewById(R.id.marketView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // pass in the lists of items
        mAdapter = new MarketAdapter(itemList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MarketAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                currentClickedItem = itemList.get(position);
                Toast.makeText(DemoViewMarketActivity.this, "This is a demo. " +
                        "Register and start trading now!", Toast.LENGTH_LONG).show();
            }
        });

    }
}
