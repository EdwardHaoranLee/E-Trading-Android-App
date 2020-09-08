package com.example.phase2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.entity.DemoUser;
import com.example.phase2.gateway.UserData;


import java.io.InputStream;

public class DemoRequestAddingItemActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE =
            "com.example.phase2.EXTRA_IMAGE";
    private static final String TAG = "Group0116_Message:";
    private InputStream inputStream;
    private Bitmap bitmap;
    private ImageView itemImage;
    private DemoUser currentUser = (DemoUser) MainActivity.getUser();
    private UserData ud = MainActivity.getUserData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_adding_item);

        EditText itemName = findViewById(R.id.edit_item_name);
        EditText itemDescription = findViewById(R.id.edit_item_description);
        itemImage = findViewById(R.id.item_image);



        // changing status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.WashedPink));
        }

        Button chooseImage = findViewById(R.id.button_Choose_Image);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DemoRequestAddingItemActivity.this, "This is a demo. " +
                        "Register and start trading now!", Toast.LENGTH_LONG).show();

            }
        });

        Button sendRequest = findViewById(R.id.button_Send_Request_Adding);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DemoRequestAddingItemActivity.this, "This is a demo. " +
                        "Register and start trading now!", Toast.LENGTH_LONG).show();

                }

        });

        // backwards button
        Button addItemBack = findViewById(R.id.button_Add_Item_Back);
        addItemBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoRequestAddingItemActivity.this, DemoMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            itemImage.setImageURI(imageUri);
            Drawable image = itemImage.getDrawable();
            itemImage.setTag(image);

        }
    }
}