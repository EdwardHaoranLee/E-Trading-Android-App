package com.example.phase2.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phase2.R;
import com.example.phase2.entity.Item;

import java.util.ArrayList;

public class ClickUserAdapter extends RecyclerView.Adapter<ClickUserAdapter.UserRequestsViewHolder>{

    private ArrayList<Item> items;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class UserRequestsViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;

        public UserRequestsViewHolder(@NonNull View itemView, final ClickUserAdapter.OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview1);
            textView1 = itemView.findViewById(R.id.textViewLine1);
            textView2 = itemView.findViewById(R.id.textViewLine2);

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

    public ClickUserAdapter(ArrayList<Item> itemList) {
        items = itemList;
    }

    @NonNull
    @Override
    public ClickUserAdapter.UserRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent, false);
        return new ClickUserAdapter.UserRequestsViewHolder (v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRequestsViewHolder holder, int position) {
        Item currentItem = items.get(position);

        holder.imageView.setImageBitmap(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getName());
        holder.textView2.setText(currentItem.getDescription());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
