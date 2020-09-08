package com.example.phase2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.use_case.ValidateEligibility;

public class ChangeThresholdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_threshold);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LemonYellow));
        }
        // get thresholds from admin
        EditText maxTransaction = findViewById(R.id.thresholdMaxTransactionPerWeek);
        EditText maxIncomplete = findViewById(R.id.maxIncompleteTransactions);
        EditText maxLendMinusBorrow = findViewById(R.id.thresholdLendingMinusBorrowing);
        EditText maxEditing = findViewById(R.id.thresholdMaxEditing);


        // change thresholds
        Button changeThresholds = findViewById(R.id.button_Confirm_Thresholds);
        changeThresholds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!maxTransaction.getText().toString().equals("") && !maxIncomplete.getText().toString().equals("")
                && !maxLendMinusBorrow.getText().toString().equals("") && !maxEditing.getText().toString().equals("")) {

                    int maxT = Integer.parseInt(maxTransaction.getText().toString());
                    int maxI = Integer.parseInt(maxIncomplete.getText().toString());
                    int maxL = Integer.parseInt(maxLendMinusBorrow.getText().toString());
                    int maxE = Integer.parseInt(maxEditing.getText().toString());
                    ValidateEligibility.setThresholdMaxTransactionPerWeek(maxT);
                    ValidateEligibility.setMaxIncompleteTransactions(maxI);
                    ValidateEligibility.setThresholdLendingMinusBorrowing(maxL);
                    ValidateEligibility.setThresholdMaxEditing(maxE);
                    Toast.makeText(ChangeThresholdActivity.this, "Changed Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ChangeThresholdActivity.this, "At Least One of the Fields Above is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // back button
        Button backButton = findViewById(R.id.button_Change_Threshold_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeThresholdActivity.this, AdminSettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}