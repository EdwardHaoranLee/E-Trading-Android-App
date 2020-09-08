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
import com.example.phase2.entity.User;

public class AdChangePassActivity extends AppCompatActivity {

    private EditText newPasswordVIew;
    private EditText confirmPassView;
    private User user = MainActivity.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_change_pass);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LemonYellow));
        }

        Button changePassBackButton = findViewById(R.id.button_Ad_Change_Pass_Back);
        changePassBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdChangePassActivity.this, AdminSettingsActivity.class);
                startActivity(intent);
            }
        });

        final Button signUpButton = findViewById(R.id.ad_confirm_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmPass();
            }
        });
    }

    private void confirmPass () {
        newPasswordVIew = findViewById(R.id.editTextAdChangeNewPassword);
        confirmPassView = findViewById(R.id.editTextAdChangeConfirmPassword);
        String conPass = confirmPassView.getText().toString();
        String newPass = newPasswordVIew.getText().toString();

        if (!newPass.equals(conPass)) {
            Toast.makeText(this, "Passwords don't match! Please Reenter your passwords", Toast.LENGTH_SHORT).show();
        }
        else if (newPass .isEmpty() && conPass.isEmpty()) {
            Toast.makeText(this, "Empty Password, please enter again!", Toast.LENGTH_SHORT).show();
        }
        else {
            user.setPassword(conPass);
            Toast.makeText(this, "Changed Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}