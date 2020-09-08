package com.example.phase2.presenter;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HideComponents {

    public static void hideButton(Button button){
        button.setVisibility(View.GONE);
    }

    public static void hideTextView(TextView textView){
        textView.setVisibility(View.GONE);
    }

}
