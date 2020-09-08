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
import com.example.phase2.controller.ItemSuggestion;
import com.example.phase2.entity.Item;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.gateway.UserData;
import com.example.phase2.use_case.CleanText;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TwoWayChooseItemActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SmartSuggestionAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RegularUser user = (RegularUser) MainActivity.getUser();
    private UserData ud = MainActivity.getUserData();
    private ArrayList<Item> itemList = new ArrayList<>();
    private static Item currentClickedItem;
    private static Item selectedItem;
    public static final String EXTRA_CLICKED =
            "com.example.phase2.EXTRA_CLICKED";

    public static Item getSelectedItem() {
        return selectedItem;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_way_choose_item);

        EditText theFilter = findViewById(R.id.smart_item_search_bar);
        Item currentWantingItem = TwoWayTradeActivity.getCurrentWantingItem();
        RegularUser theOtherUser = (RegularUser) ud.findUser(currentWantingItem.getUsername());

        // STARTS HERE:

        List<String> stopwords = new ArrayList<>();
        try {

            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("stopwords.txt") );
            Scanner scanner = new Scanner(inputReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().replaceAll("[\\s+]", "");
                stopwords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ENDS HERE:

        // give an exact match between this user's wish to lend list and the other user's wish list
        // or give a list of items ranked by similarity
        CleanText ct = new CleanText(stopwords);
        ItemSuggestion itemSuggestion = new ItemSuggestion(user, theOtherUser);
        if (theOtherUser.getWillingToBorrowItems().size() != 0) {
            if (itemSuggestion.tradeSuggestion().size() != 0) {
                itemList = itemSuggestion.tradeSuggestion();
            }
            else {
                itemList = itemSuggestion.smartSuggestion(ct);
            }
        }
        else {
            itemList = user.getWillingToLendItems();
        }


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
        Button marketBackButton = findViewById(R.id.button_Smart_Back);
        marketBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TwoWayChooseItemActivity.this, TwoWayTradeActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    // build recyclerview
    public void buildRecyclerView() {

        mRecyclerView = findViewById(R.id.smartItemView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        // pass in the lists of items
        mAdapter = new SmartSuggestionAdapter(itemList, TwoWayChooseItemActivity.this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SmartSuggestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                currentClickedItem = itemList.get(position);
//                Intent intent = new Intent(TwoWayChooseItemActivity.this, MarketItemClickedActivity.class);
//                startActivity(intent);
            }

            @Override
            public void onAddClick(int position) {
                selectedItem = itemList.get(position);
                Intent intent = new Intent ();
                intent.putExtra(EXTRA_CLICKED, true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
