package com.example.phase2.activities;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phase2.R;
import com.example.phase2.entity.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ApproveItemAdapter extends RecyclerView.Adapter<ApproveItemAdapter.ItemRequestViewHolder> {

    private HashMap<String, ArrayList<Item>> itemRequests;
    private ApproveItemAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemRequestClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ItemRequestViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;

        public ItemRequestViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview1);
//            textView2 = itemView.findViewById(R.id.textViewUserName);
//            textView3 = itemView.findViewById(R.id.textViewItemNum);
            textView2 = itemView.findViewById(R.id.textViewLine1);
            textView3 = itemView.findViewById(R.id.textViewLine2);
            textView1 = itemView.findViewById(R.id.textViewLine3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }

    public ApproveItemAdapter(HashMap<String, ArrayList<Item>> itemRequestsList) {
        itemRequests = itemRequestsList;
    }

    @Override
    @NonNull
    public ItemRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemRequestViewHolder(v, mListener);
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onBindViewHolder(@NonNull ItemRequestViewHolder holder, int position) {
        int currentKey = -1;

        // Getting an iterator
        Iterator<Map.Entry<String, ArrayList<Item>>> userIterator = itemRequests.entrySet().iterator();
        if (itemRequests.size() >= 1){
            while (userIterator.hasNext()) {
                Map.Entry<String, ArrayList<Item>> mapElement = userIterator.next();
                currentKey += 1;
                if (currentKey == position) {

                    ArrayList<Item> items = mapElement.getValue();
                    int itemNum = items.size();
                    String username = mapElement.getKey();
                    if (items.size() > 0){

                        Bitmap d = items.get(0).getImageResource();
                        String b = "Items To Approve";

                        holder.imageView.setImageBitmap(d);
                        holder.textView2.setText(b);
                    }
                    else {
                        String b = "Items To Approve";

                        holder.textView2.setText(b);
                    }
                    holder.textView3.setText(String.valueOf(itemNum));
                    holder.textView1.setText(username);

                }

            }
        }

    }

    @Override
    public int getItemCount() {
        return itemRequests.size();
    }
}
