package com.example.phase2.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.database.SaveData;
import com.example.phase2.entity.AdminUser;
import com.example.phase2.entity.Item;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.gateway.UserData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestAddingItemActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE =
            "com.example.phase2.EXTRA_IMAGE";
    private static final String TAG = "Group0116_Message:";
    private InputStream inputStream;
    private ImageView itemImage;
    private EditText itemName;
    private EditText itemDescription;
    private RegularUser currentUser = (RegularUser) MainActivity.getUser();
    private UserData ud = MainActivity.getUserData();


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_adding_item);

        itemName = findViewById(R.id.edit_item_name);
        itemDescription = findViewById(R.id.edit_item_description);
        itemImage = findViewById(R.id.item_image);


        // set default image
        final int resourceId = getResources().getIdentifier("default_image", "drawable", getPackageName());
        Bitmap a = ((BitmapDrawable) getDrawable(resourceId)).getBitmap();
        String defaultImagePath = this.saveImage("default_image", a);
        itemImage.setImageBitmap(a);

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
                if (ActivityCompat.checkSelfPermission(RequestAddingItemActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // request the permission
                    ActivityCompat.requestPermissions(RequestAddingItemActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            100);
                    return;
                }
                // get image from gallery
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);

            }
        });

        Button sendRequest = findViewById(R.id.button_Send_Request_Adding);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {

                if (currentUser.getIsBusy()) {
                    Toast.makeText(RequestAddingItemActivity.this, "You seems on vacation now, turn off the vacation mode to use this function", Toast.LENGTH_SHORT).show();
                }

                if (itemName.getText() == null) {
                    Toast.makeText(RequestAddingItemActivity.this, "Name of the Item shouldn't be empty!", Toast.LENGTH_SHORT).show();
                }
                if (itemDescription.getText() == null) {
                    Toast.makeText(RequestAddingItemActivity.this, "Description of the Item shouldn't be empty!", Toast.LENGTH_SHORT).show();
                }
                if (itemImage.getDrawable() == null) {
                    Toast.makeText(RequestAddingItemActivity.this, "Image of the Item shouldn't be empty!", Toast.LENGTH_SHORT).show();
                }
                if (itemName.getText() != null && itemDescription.getText() != null && itemImage.getDrawable() != null) {

                    String Name = itemName.getText().toString();
                    String Description = itemDescription.getText().toString();
                    Bitmap imageBitmap = ((BitmapDrawable) itemImage.getDrawable()).getBitmap();
                    Item item = new Item(Name, Description, saveImage(Name, imageBitmap));
                    String username = currentUser.getUsername();
                    item.setUsername(username);
//                    ArrayList<Item> items = new ArrayList<>();
//                    items.add(item);

//                    AdminUser admin = (AdminUser) ud.findUser("PrimaryAdmin");
//                    admin.setItemRequest(currentUser.getUsername(), items);

                    // add the request to admin's list
                    AdminUser admin = (AdminUser) ud.findUser("PrimaryAdmin");
                    HashMap<String, ArrayList<Item>> map = admin.getItemRequests();

                    if (map.containsKey(currentUser.getUsername())) {
                        map.get(currentUser.getUsername()).add(item);
                    }
                    else {

                        ArrayList<Item> items = new ArrayList<>();
                        items.add(item);
                        admin.setItemRequests(currentUser.getUsername(), items);
                    }
//                    RequestAddingItem addRequest = new RequestAddingItem();
//                    addRequest.addingToFiles(currentUser.getUsername(), item);

                    Intent intent = new Intent(RequestAddingItemActivity.this, RequestSentActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        // backwards button
        Button addItemBack = findViewById(R.id.button_Add_Item_Back);
        addItemBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestAddingItemActivity.this, RegularMenuActivity.class);
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

    @RequiresApi(api = Build.VERSION_CODES.R)
    private String saveImage(String name, Bitmap image){
        try {
            String path = SaveData.context.getFilesDir().getPath() + "/item_image";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, name + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
            return name + ".png";
        } catch (IOException e){
            System.out.println("this is not possible.");
            return null;
        }

    }
}