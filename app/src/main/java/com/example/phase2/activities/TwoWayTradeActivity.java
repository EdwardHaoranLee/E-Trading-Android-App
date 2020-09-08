package com.example.phase2.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.controller.RequestTrade;
import com.example.phase2.database.SaveData;
import com.example.phase2.entity.Item;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.entity.TransactionRequest;
import com.example.phase2.exception.ItemNotApprovedException;
import com.example.phase2.exception.NotValidForTransactionException;
import com.example.phase2.gateway.UserData;

import java.util.ArrayList;
import java.util.Calendar;

public class TwoWayTradeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView newDate;
    private TextView newTime;
    private EditText newLocation;
    private ImageView itemImage1;
    private ImageView itemImage2;
    private Item selectedItem;
    private RegularUser user = (RegularUser) MainActivity.getUser();
    private UserData ud = MainActivity.getUserData();
    private int newYear, newMonth, newDay, newHour, newMinute;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private String Temporary;
    private boolean isTemporary;
    private static final int CHOOSE_ITEM_CODE = 1;
    private static Item currentWantingItem;

    public static Item getCurrentWantingItem() {
        return currentWantingItem;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_way_trade);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.WashedPink));
        }

        Spinner spinner = findViewById(R.id.spinner_two_way);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        currentWantingItem = ViewMarketActivity.getCurrentClickedItem();
        RegularUser theOtherUser = (RegularUser) ud.findUser(currentWantingItem.getUsername());

        newDate = findViewById(R.id.date_two_way);
        newTime = findViewById(R.id.time_two_way);
        newLocation = findViewById(R.id.location_two_way);

        // set image for one of the items
        itemImage2 = findViewById(R.id.two_way_image_2);
        Bitmap image1 = currentWantingItem.getImageResource();
        itemImage2.setImageBitmap(image1);

        // click choose item to trade
        Button chooseItem = findViewById(R.id.choose_item_two_way_button);
        chooseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TwoWayTradeActivity.this, TwoWayChooseItemActivity.class);
                startActivityForResult(intent, CHOOSE_ITEM_CODE);
            }
        });

        // back button
        Button backButton = findViewById(R.id.button_back_two_way);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TwoWayTradeActivity.this, MarketItemClickedActivity.class);
                startActivity(intent);
            }
        });


        // click send request button
        Button sendRequest = findViewById(R.id.send_two_way_button);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {

                if (user.getIsBusy()) {
                    Toast.makeText(TwoWayTradeActivity.this, "You seems on vacation now, turn off the vacation mode to use this function", Toast.LENGTH_SHORT).show();
                }

                if (user.getIsFrozen()) {
                    Toast.makeText(TwoWayTradeActivity.this, "Your Account Is Frozen Now", Toast.LENGTH_SHORT).show();
                }

                if (!newDate.getText().toString().equals("") && !newTime.getText().toString().equals("") && !newLocation.getText().toString().equals("") && selectedItem != null) {
                    String location = newLocation.getText().toString();
                    ArrayList<Item> items = new ArrayList<>();
                    items.add(currentWantingItem);
                    items.add(selectedItem);


                    //check if the request is temporary
                    if (Temporary.equals("Temporary")) {
                        isTemporary = true;
                    }
                    else {
                        isTemporary = false;
                    }

                    RequestTrade tradeRequest = null;
                    try {

                       tradeRequest = new RequestTrade(
                                (RegularUser) user, (RegularUser) theOtherUser, selectedItem);

                    } catch (ItemNotApprovedException e) {
                        e.printStackTrace();
                    }

                    try {

                        TransactionRequest transactionRequest = tradeRequest.requestTrade(newYear, newMonth, newDay, newHour, newMinute,
                                location, items, true, isTemporary);
                        theOtherUser.setTransactionRequests(transactionRequest);
                        Toast.makeText(TwoWayTradeActivity.this, "Request Sent Successfully", Toast.LENGTH_SHORT).show();

                    } catch (NotValidForTransactionException e) {
                        SaveData saveData = new SaveData();
                        saveData.saveData(theOtherUser);
                        Toast.makeText(TwoWayTradeActivity.this, "You Are Not Valid For This Trade", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(TwoWayTradeActivity.this, "At least one of fields above is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //select new date
        newDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(TwoWayTradeActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        // select new time
        newTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(TwoWayTradeActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, timeSetListener,
                        hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                newYear = year;
                newMonth = month;
                newDay = day;
                String date = month + "/" + day + "/" + year;
                newDate.setText(date);
            }
        };

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {

                String time = hour + ":" + min;
                newHour = hour;
                newMinute = min;
                newTime.setText(time);
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_ITEM_CODE && resultCode == RESULT_OK) {
            assert data != null;
            boolean clicked = data.getBooleanExtra(TwoWayChooseItemActivity.EXTRA_CLICKED, false);
            if (clicked) {
                selectedItem = TwoWayChooseItemActivity.getSelectedItem();
                // set image for another item
                itemImage1 = findViewById(R.id.two_way_image_1);
                Bitmap image = selectedItem.getImageResource();
                itemImage1.setImageBitmap(image);
            }
        }
    }

    // choose permanent or temporary
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Temporary = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}