package com.example.phase2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.controller.UnfreezeRequestCollection;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.entity.User;
import com.example.phase2.gateway.UserData;
import com.example.phase2.presenter.HideComponents;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class FreezeUnfreezeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeze_unfreeze);

//        ArrayList<RegularUser> freezingRegularUsers = userData.getFrozenUsers();
//        ArrayList<RegularUser> requestRegularUsers = new ArrayList<>();
//        for(RegularUser user: freezingRegularUsers){
//            if(user.getIsUnfreezeRequestSent()){
//                requestRegularUsers.add(user);
//            }
//        }
//        HashMap<String, RegularUser> nameToRegularUser = setUpFreezingMap(requestRegularUsers);
//
//
//        Button goBackButton = findViewById(R.id.button_Inventory_Back_toback_hhhh);
//        goBackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(FreezeUnfreezeActivity.this, AdminMenuActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        TextView freezeDescription = findViewById(R.id.text_view_freeze_description_hhhh);
//
//        TextView usernameTitle = findViewById(R.id.text_view_name_hhhh);
//
//        TextView descriptionTitle = findViewById(R.id.text_view_behaviour_hhhh);
//
//        TextView status = findViewById(R.id.textView_status_hhhh);
//
//        TextView descriptionDemo = findViewById(R.id.textView_description_demo_hhhh);
//
//        TextView usernameDemo = findViewById(R.id.textView_name_demo_hhhh);
//
//        ImageView imageView = findViewById(R.id.imageview_user_image_hhhh);
//
//        Button buttonPrevious = findViewById(R.id.previously_user_hhhh);
//
//        Button buttonNext = findViewById(R.id.nextly_user_hhhh);
//
//        Button unfreezeUser = findViewById(R.id.button_unfreeze_hhhh);
//
//        ArrayList<TextView> textViews = new ArrayList<>();
//        ArrayList<Button> buttons = new ArrayList<>();
//        ArrayList<ImageView> imageViews = new ArrayList<>();
//
//        textViews.add(usernameDemo);
//        textViews.add(descriptionDemo);
//        textViews.add(status);
//        textViews.add(descriptionTitle);
//        textViews.add(usernameTitle);
//        textViews.add(freezeDescription);
//
//        imageViews.add(imageView);
//        buttons.add(buttonPrevious);
//        buttons.add(buttonNext);
//        buttons.add(unfreezeUser);
//
//
//        if(freezingRegularUsers.size() == 0){
//            setUpForNoFreezingUsers(textViews, buttons, imageViews);
//            Toast.makeText(FreezeUnfreezeActivity.this, "There are currently no freezing regular users to request unfreeze. Please go back to the Admin User menu", Toast.LENGTH_LONG).show();;
//        }else{
//            RegularUser defaultUserShown = freezingRegularUsers.get(this.count);
//            showUser(defaultUserShown.getUsername(), usernameDemo, descriptionDemo);
//        }
//
//        buttonPrevious.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rotate(requestRegularUsers,true);
//                showUser(currentUser.getUsername(), usernameDemo, descriptionDemo);
//            }
//        });
//
//        buttonNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                currentUser = rotate(requestRegularUsers, false);
//                showUser(currentUser.getUsername(), usernameDemo, descriptionDemo);
//            }
//        });
//
//        unfreezeUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!nameToRegularUser.containsKey(usernameDemo.getText().toString())){
//                    return;
//                }
//                RegularUser getUser = nameToRegularUser.get(usernameDemo.getText().toString());
//                getUser.setFrozen(false);
//                getUser.setUnfreezeRequest(null);
//                getUser.setIsUnfreezeRequestSent(false);
//                currentUser = rotate(requestRegularUsers, false);
//                freezingRegularUsers.remove(getUser);
//                requestRegularUsers.remove(getUser);
//                unfreezeRequestCollection.cleanOut(usernameDemo.getText().toString());
//                showUser(currentUser.getUsername(), usernameDemo, descriptionDemo);
//            }
//        });
//    }
//
//    private void showUser(String username, TextView textUsername, TextView description){
//        textUsername.setText(username);
//        description.setText(unfreezeRequestCollection.getUserRequest(username));
//    }
//
//    /**
//     *
//     * @param freezingRegularUsers
//     * @param isFront true iff you want to go previous and false iff you want to go next
//     * @return target regular user
//     */
//    private RegularUser rotate(ArrayList<RegularUser> freezingRegularUsers, Boolean isFront){
//        if(isFront){
//            if(this.count <= 0){
//                this.count = freezingRegularUsers.size() - 1;
//            }else{
//                this.count--;
//            }
//        }else{
//            if(this.count >= freezingRegularUsers.size() - 1){
//                this.count ++;
//            }else{
//                this.count = 0;
//            }
//        }
//        return freezingRegularUsers.get(this.count);
//    }
//
//    private HashMap<String, RegularUser> setUpFreezingMap(ArrayList<RegularUser> freezingRegularUsers){
//        HashMap<String, RegularUser> nameToUser = new HashMap<>();
//        for(RegularUser regularUser: freezingRegularUsers){
//            nameToUser.put(regularUser.getUsername(), regularUser);
//        }
//        return nameToUser;
//    }
//
//    @SuppressLint("SetTextI18n")
//    private void setUpForNoFreezingUsers(ArrayList<TextView> textViews, ArrayList<Button> buttons, ArrayList<ImageView> imageViews){
//        for (TextView textView: textViews){
//            HideComponents.hideTextView(textView);
//        }
//
//        for(Button button: buttons){
//            HideComponents.hideButton(button);
//        }
//
//        for(ImageView imageView: imageViews){
//            imageView.setVisibility(View.GONE);
//        }

    }
}