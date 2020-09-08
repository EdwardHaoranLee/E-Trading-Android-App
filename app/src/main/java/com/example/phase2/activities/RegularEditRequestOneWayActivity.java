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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.controller.ConfirmMeeting;
import com.example.phase2.database.SaveData;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.entity.TransactionRequest;
import com.example.phase2.gateway.UserData;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class RegularEditRequestOneWayActivity extends AppCompatActivity {

    private TextView oldTime;
    private TextView oldLocation;
    private TextView newDate;
    private TextView newTime;
    private TextView itemName;
    private TextView itemDescription;
    private TextInputEditText newLocation;
    private ImageView itemImage1;
    private TransactionRequest transactionRequest;
    private RegularUser user = (RegularUser) MainActivity.getUser();
    private UserData ud = MainActivity.getUserData();
    private int newYear, newMonth, newDay, newHour, newMinute;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_edit_request_one_way);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.WashedPink));
        }

        transactionRequest = PendingRequestActivity.getCurrentClickedRequest();

        oldTime = findViewById(R.id.request_time_one);
        oldLocation = findViewById(R.id.request_location_one);

        itemImage1 = findViewById(R.id.item_one_way);

        newDate = findViewById(R.id.edit_date_one);
        newTime = findViewById(R.id.edit_time_one);
        newLocation = findViewById(R.id.edit_location_one);

        // set images for this item
        Bitmap image1 = transactionRequest.getItemsToTrade().get(0).getImageResource();
        itemImage1.setImageBitmap(image1);

        // set name and description for this item
        itemName = findViewById(R.id.item_name_one);
        itemDescription = findViewById(R.id.item_description_one);
        itemName.setText(transactionRequest.getItemsToTrade().get(0).getName());
        itemDescription.setText(transactionRequest.getItemsToTrade().get(0).getDescription());

        // set old time
        int oldYear = transactionRequest.getTime().getYear();
        int oldMon = transactionRequest.getTime().getMonthValue();
        int oldDay = transactionRequest.getTime().getDayOfMonth();
        int oldHr = transactionRequest.getTime().getHour();
        int oldMin = transactionRequest.getTime().getMinute();
        String oldT = oldMon + "/" + oldDay + "/" + oldYear + " " + oldHr + ":" + oldMin;
        oldTime.setText(oldT);

        // set old location
        String local = transactionRequest.getLocation();
        oldLocation.setText(local);

        // click send request button
        Button sendRequest = findViewById(R.id.re_edit_request_one_button);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {

                if (user.getIsBusy()) {
                    Toast.makeText(RegularEditRequestOneWayActivity.this, "You seems on vacation now, turn off the vacation mode to use this function", Toast.LENGTH_SHORT).show();
                }

                if (newDate != null && newTime != null && newLocation != null) {
                    String local = newLocation.getText().toString();
                    int result = transactionRequest.modifyRequest(local, newYear, newMonth,
                            newDay, newHour, newMinute);
                    if (result == 0) {

                        RegularUser theOtherUser = (RegularUser) ud.findUser(transactionRequest.getTheOtherUser().getUsername());
                        theOtherUser.getTransactionRequests().add(transactionRequest);
                        user.getTransactionRequests().remove(transactionRequest);
                        SaveData saveData = new SaveData();
                        saveData.saveData(theOtherUser);
                        finish();
                        Toast.makeText(RegularEditRequestOneWayActivity.this, "Request Sent Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegularEditRequestOneWayActivity.this, "This Request Cannot be Edited Anymore, Accept or Deny It Instead", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RegularEditRequestOneWayActivity.this, "At least one of fields above is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // click accept request button
        Button acceptRequest = findViewById(R.id.re_accept_request_one_button);
        acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user.getIsBusy()) {
                    Toast.makeText(RegularEditRequestOneWayActivity.this, "You seems on vacation now, turn off the vacation mode to use this function", Toast.LENGTH_SHORT).show();
                }

                transactionRequest.setAccepted(true);
                ConfirmMeeting confirm = new ConfirmMeeting(transactionRequest);
                confirm.confirmMeeting();
                RegularUser theOtherUser = (RegularUser) ud.findUser(transactionRequest.getTheOtherUser().getUsername());
                theOtherUser.getTransactionRequests().add(transactionRequest);
                user.getTransactionRequests().remove(transactionRequest);
                SaveData saveData = new SaveData();
                saveData.saveData(theOtherUser);
                finish();
                Toast.makeText(RegularEditRequestOneWayActivity.this, "Request Accepted", Toast.LENGTH_SHORT).show();
            }
        });

        // back to requests list
        Button backButton = findViewById(R.id.button_edit_request_back_two);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegularEditRequestOneWayActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                finish();
            }
        });

        newDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegularEditRequestOneWayActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar, dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        newTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(RegularEditRequestOneWayActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar, timeSetListener,
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
}