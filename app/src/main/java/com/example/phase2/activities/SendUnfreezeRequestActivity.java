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
import com.example.phase2.entity.RegularUser;

public class SendUnfreezeRequestActivity extends AppCompatActivity {

    private RegularUser user = (RegularUser) MainActivity.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_unfreeze_request);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.WashedPink));
        }

        // send request
        Button unfreezeRequestButton = findViewById(R.id.send_unfreeze_request_button);
        unfreezeRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editRequest = findViewById(R.id.edit_unfreeze_request);
                String request = editRequest.getText().toString();
                if (request.equals("")) {
                    Toast.makeText(SendUnfreezeRequestActivity.this, "Request Cannot Be Empty", Toast.LENGTH_SHORT).show();
                }
                if (!user.getIsFrozen()) {
                    Toast.makeText(SendUnfreezeRequestActivity.this, "You Cannot Unfreeze Since Your Account Is Not Frozen", Toast.LENGTH_SHORT).show();
                }
                if (!request.equals("") && user.getIsFrozen()) {
                    user.setUnfreezeRequest(request);
                    Toast.makeText(SendUnfreezeRequestActivity.this, "Request Sent Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // back to regular menu
        Button unfreezeBackButton = findViewById(R.id.button_back_unfreeze_request);
        unfreezeBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminMenuActivity.setUnfreezeClicked(false);
                Intent intent = new Intent(SendUnfreezeRequestActivity.this, ReAccountInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}