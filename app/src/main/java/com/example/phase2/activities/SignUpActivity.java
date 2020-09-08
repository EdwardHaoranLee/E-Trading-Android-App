package com.example.phase2.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.example.phase2.UIs.LoginManager;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.entity.User;
import com.example.phase2.gateway.UserData;

public class SignUpActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME =
            "com.example.phase2.EXTRA_USERNAME";
    public static final String EXTRA_PASSWORD =
            "com.example.phase2.EXTRA_PASSWORD";
    private static final String TAG = "Group0116_Message:";
    private UserData ud = MainActivity.getUserData();
    private EditText userNameView;
    private EditText newPasswordVIew;
    private EditText confirmPassView;


    private ProgressDialog LoadingBar;
    private static RegularUser user;
    public static User getUser() {return user;}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.MildGreen));
        }

        userNameView = findViewById(R.id.editTextUsername2);
        newPasswordVIew = findViewById(R.id.editTextNewPassword);
        confirmPassView = findViewById(R.id.editTextConfirmPassword);

        // go backwards
        final Button backwards_Button = findViewById(R.id.button_Sign_Up_Back);
        backwards_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // sign up
        final Button signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void signUp() {
        String name = userNameView.getText().toString();
        String conPass = confirmPassView.getText().toString();
        String newPass = newPasswordVIew.getText().toString();
        LoginManager loginManager = new LoginManager(ud);

        if (name.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty, please enter again!", Toast.LENGTH_SHORT).show();
        }

        if (!newPass.equals(conPass)) {
            Toast.makeText(this, "Passwords don't match! Please Reenter your passwords", Toast.LENGTH_SHORT).show();
        }
        if (newPass.isEmpty() && conPass.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty, please enter again!", Toast.LENGTH_SHORT).show();
        }

        if (!name.isEmpty() && (!newPass.isEmpty() && !conPass.isEmpty()) && newPass.equals(conPass)) {

            // show the loading progress
            LoadingBar = new ProgressDialog(this);
            LoadingBar.setTitle("Creating Account");
            LoadingBar.setMessage("A Moment Please, We Are Creating Your Account");
            LoadingBar.setCanceledOnTouchOutside(true);
            LoadingBar.show();

            int result = loginManager.register(name,conPass);

            if (result == 1) {
                LoadingBar.dismiss();
                user = (RegularUser) ud.findUser(name);
                // for testing
//                final int resourceId = getResources().getIdentifier("login_background", "drawable", getPackageName());
//                Drawable a = getDrawable(resourceId);
//                user.addInventory(new Item("Title", "Description", a));
                Intent data = new Intent();
                data.putExtra(EXTRA_USERNAME, name);
                data.putExtra(EXTRA_PASSWORD, conPass);
                setResult(RESULT_OK, data);
                finish();
                Toast.makeText(SignUpActivity.this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();
            }
            else if (result == 0) {
                Toast.makeText(SignUpActivity.this, "This username already exists! Please try again.", Toast.LENGTH_SHORT).show();
            }

        }


//    @Override
//    public boolean onClicked(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.sign_up_button:
//                signUp();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    }



    @Override
    protected void onStart () {
        super.onStart();
    }
}