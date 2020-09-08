package com.example.phase2.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.entity.*;
import com.example.phase2.database.*;

import com.example.phase2.gateway.Initializer;
import com.example.phase2.gateway.UserData;

public class MainActivity extends AppCompatActivity {

    public static final int SIGN_UP_REQUEST = 1;
    private static UserData userData;

    // For facebook sign in
    private ProgressDialog LoadingBar;

    //For demo feature
    private Button demoButton;



    // for other activity to communicate with the database
    public static UserData getUserData() {
        return userData;
    }
    private static final String TAG = "Group0116_Message:";
    private static User user;
    public static User getUser() {return user;}


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SaveData saveData = new SaveData(this);
        System.out.println(SaveData.context.getFilesDir().getPath());
        MainActivity.userData = Initializer.initialize();
        setContentView(R.layout.activity_main);

        // Demo RegularUser For Test
        // Make sure there are no frozen user in the algorithm
        //  \---------------------------\   \---------------------------\   \---------------------------\
//          \       RegularUser         \   \       RegularUser         \   \       RegularUser         \
//          \Username: Kelvin			\   \Username: Cissy			\   \Username: Gary 			\
//          \Password: kel				\   \Password: cis				\   \Password: gar				\
//          \IsFrozen : true		    \   \IsFrozen : true		    \   \IsFrozen : false		    \
//          \Non unfreeze request sent	\   \Non unfreeze request sent	\   \---------------------------\
//          \---------------------------\   \---------------------------\

        boolean checkIsStored = false;
        for(RegularUser regularUser: userData.getRegularUsers()){
            if(regularUser.getUsername().equals("Kelvin")){
                checkIsStored =  true;
            }
        }

        // Avoid that these three users reset
        if(!checkIsStored) {
            userData.getRegularUsers().clear();
            RegularUser kelvin = new RegularUser("Kelvin", "kel");
            RegularUser cissy = new RegularUser("Cissy", "cis");
            RegularUser gary = new RegularUser("Gary", "gar");
            kelvin.setFrozen(true);
            cissy.setFrozen(true);
            userData.addUser(kelvin);
            userData.addUser(cissy);
            userData.addUser(gary);
        }



        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.MildGreen));
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        System.out.println(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE));
//        System.out.println(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE));
//        System.out.println(Environment.getExternalStorageState());
        // Test only
        // Log in button set up

        // A window that ask for unfreeze request





        Button buttonLogIn = (Button) findViewById(R.id.login_button);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {
                EditText Username = findViewById(R.id.editTextUsername2);
                EditText Password = findViewById(R.id.editTextPassword);
                String name = Username.getText().toString();;
                String password = Password.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Username cannot be empty, please enter again!", Toast.LENGTH_SHORT).show();
                }

                if (password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Password cannot be empty, please enter again!", Toast.LENGTH_SHORT).show();
                }

                if(!(Username.getText() == null) && !(Password.getText() ==null)) {

                    // show the loading progress
                    LoadingBar = new ProgressDialog(MainActivity.this);
                    LoadingBar.setTitle("Logging In");
                    LoadingBar.setMessage("A Moment Please");
                    LoadingBar.setCanceledOnTouchOutside(true);
                    LoadingBar.show();


                    int validation = userData.validateUser(name, password);
                    switch (validation) {
                        case -1:
                            // dismiss the loading bar
                            LoadingBar.dismiss();

                            Toast.makeText(MainActivity.this, "This username does not exist. Please try again.",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case 0:
                            // dismiss the loading bar
                            LoadingBar.dismiss();

                            Toast.makeText(MainActivity.this, "Password incorrect! Please try again.",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            // dismiss the loading bar
                            LoadingBar.dismiss();

                            user = userData.findUser(name);
                            if (user.getUserType() == 1) {
                                Intent intent = new Intent(MainActivity.this, RegularMenuActivity.class);
                                startActivity(intent);
                            }
                            else if (user.getUserType() == 3) {
                                Intent intent = new Intent(MainActivity.this, DemoMenuActivity.class);
                                startActivity(intent);
                            }
                            else if (user.getUserType() == 0) {
                                // dismiss the loading bar
                                LoadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, AdminMenuActivity.class);
                                startActivity(intent);
                            }
                            SaveData savingData = new SaveData();
                            savingData.saveData(user);
                    }
                }
            }
        });

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // sign up
        final Button toSignUpButton = findViewById(R.id.sign_up_reminder);
        toSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivityForResult(intent, SIGN_UP_REQUEST);
            }
        });


        demoButton = (Button) findViewById(R.id.sign_in_with_demo);
        demoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = userData.getDemoUser();
                Intent intent = new Intent(MainActivity.this, DemoMenuActivity.class);
                startActivity(intent);
            }
        });






        Log.i(TAG, "onCreate");
        Log.i(TAG, Environment.getStorageDirectory().getAbsolutePath().toString() + SaveData.ITEM_INVENTORY_FILENAME);
    }

//    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mainFirebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mainFirebaseAuth.getCurrentUser();
//                            Intent intent = new Intent(MainActivity.this, RegularMenuActivity.class);
//                            startActivity(intent);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                        // ...
//                    }
//                });
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_UP_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(SignUpActivity.EXTRA_USERNAME);
            String password = data.getStringExtra(SignUpActivity.EXTRA_PASSWORD);
            EditText usernameView = findViewById(R.id.editTextUsername2);
            EditText PasswordVIew = findViewById(R.id.editTextPassword);
            usernameView.setText(name);
            PasswordVIew.setText(password);
        }

        Log.i(TAG, "onActivityResult");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.i(TAG, "onCreateOptionsMenus");
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(User user: userData.getUsers()){
            SaveData savingData = new SaveData();
            savingData.saveData(user);
        }
        Log.i(TAG, "onDestroy");
    }

}