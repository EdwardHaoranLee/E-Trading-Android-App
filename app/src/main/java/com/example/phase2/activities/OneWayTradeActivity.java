package com.example.phase2.activities;

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
import com.example.phase2.entity.Item;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.entity.TransactionRequest;
import com.example.phase2.exception.ItemNotApprovedException;
import com.example.phase2.exception.NotValidForTransactionException;
import com.example.phase2.gateway.UserData;

import java.util.ArrayList;
import java.util.Calendar;

public class OneWayTradeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageView itemImage1;
    private Item currentWantingItem;
    private TextView newDate;
    private TextView newTime;
    private EditText newLocation;
    private RegularUser user = (RegularUser) MainActivity.getUser();
    private UserData ud = MainActivity.getUserData();
    private int newYear, newMonth, newDay, newHour, newMinute;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private String Temporary;
    private boolean isTemporary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_way_trade);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.WashedPink));
        }

        currentWantingItem = ViewMarketActivity.getCurrentClickedItem();
        newDate = findViewById(R.id.date_one_way);
        newTime = findViewById(R.id.time_one_way);
        newLocation = findViewById(R.id.location_one_way);

        // set image for the item
        itemImage1 = findViewById(R.id.one_way_image);
        Bitmap image1 = currentWantingItem.getImageResource();
        itemImage1.setImageBitmap(image1);

        RegularUser theOtherUser = (RegularUser) ud.findUser(currentWantingItem.getUsername());

        Spinner spinner = findViewById(R.id.spinner_one_way);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // back button
        Button backButton = findViewById(R.id.button_back_one_way);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OneWayTradeActivity.this, MarketItemClickedActivity.class);
                startActivity(intent);
            }
        });

        // click send request button
        Button sendRequest = findViewById(R.id.send_one_way_button);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if (user.getIsBusy()) {
                    Toast.makeText(OneWayTradeActivity.this, "You seems on vacation now, turn off the vacation mode to use this function", Toast.LENGTH_SHORT).show();
                }

                if (user.getIsFrozen()) {
                    Toast.makeText(OneWayTradeActivity.this, "Your Account Is Frozen Now", Toast.LENGTH_SHORT).show();
                }

                if (!newDate.getText().toString().equals("") && !newTime.getText().toString().equals("") && !newLocation.getText().toString().equals("")) {
                    String location = newLocation.getText().toString();
                    ArrayList<Item> items = new ArrayList<>();
                    items.add(currentWantingItem);


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
                                (RegularUser) user, (RegularUser) theOtherUser, currentWantingItem);

                    } catch (ItemNotApprovedException e) {
                        e.printStackTrace();
                    }

                    try {

                        TransactionRequest transactionRequest = tradeRequest.requestTrade(newYear, newMonth, newDay, newHour, newMinute,
                                location, items, false, isTemporary);
                        theOtherUser.setTransactionRequests(transactionRequest);
                        Toast.makeText(OneWayTradeActivity.this, "Request Sent Successfully", Toast.LENGTH_SHORT).show();
                    }
                    catch (NotValidForTransactionException e) {
                    Toast.makeText(OneWayTradeActivity.this, "You Are Not Valid For This Trade", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
                else {
                    Toast.makeText(OneWayTradeActivity.this, "At least one of fields above is empty", Toast.LENGTH_SHORT).show();
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

                DatePickerDialog dialog = new DatePickerDialog(OneWayTradeActivity.this,
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

                TimePickerDialog dialog = new TimePickerDialog(OneWayTradeActivity.this,
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

    // choose permanent or temporary
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Temporary = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}