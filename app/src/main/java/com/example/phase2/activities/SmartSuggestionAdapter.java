package com.example.phase2.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phase2.R;
import com.example.phase2.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class SmartSuggestionAdapter extends RecyclerView.Adapter<SmartSuggestionAdapter.SmartItemViewHolder> implements Filterable {

    private ArrayList<Item> items;
    private ArrayList<Item> itemsFull;
    private SmartSuggestionAdapter.OnItemClickListener mListener;
    private Context context;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onAddClick(int position);
    }

    public void setOnItemClickListener(SmartSuggestionAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class SmartItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView imageIcon;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;

        public SmartItemViewHolder(@NonNull View itemView, final SmartSuggestionAdapter.OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview1);
            imageIcon = itemView.findViewById(R.id.smart_item_add_button_image);
            textView1 = itemView.findViewById(R.id.textViewLine1);
            textView2 = itemView.findViewById(R.id.textViewLine2);
            textView3 = itemView.findViewById(R.id.textViewLine3);

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

            imageIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddClick(position);
                        }
                    }
                }
            });
        }
    }

    public SmartSuggestionAdapter(ArrayList<Item> itemList, Context current) {
        items = itemList;
        itemsFull = new ArrayList<>(itemList);
        this.context = current;
    }

    @NonNull
    @Override
    public SmartSuggestionAdapter.SmartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent, false);
        return new SmartSuggestionAdapter.SmartItemViewHolder(v, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onBindViewHolder(@NonNull SmartSuggestionAdapter.SmartItemViewHolder holder, int position) {

        Item currentItem = items.get(position);

        int rankNum = position + 1;
        String rank = "Recommendation Rank : " + rankNum;
        final int resourceId = context.getResources().getIdentifier("add_icon", "drawable", context.getPackageName());
        Drawable addIcon = context.getDrawable(resourceId);
        holder.imageView.setImageBitmap(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getName());
        holder.textView2.setText(currentItem.getDescription());
        holder.textView3.setText(rank);
        holder.imageIcon.setImageDrawable(addIcon);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Item> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(itemsFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Item item : itemsFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {

            items.clear();
            items.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
