package com.example.phase2.activities;

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

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MarketViewHolder> implements Filterable {

    private ArrayList<Item> items;
    private ArrayList<Item> itemsFull;
    private MarketAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MarketAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MarketViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;

        public MarketViewHolder(@NonNull View itemView, final MarketAdapter.OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview1);
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
        }
    }

    public MarketAdapter(ArrayList<Item> itemList) {
        items = itemList;
        itemsFull = new ArrayList<>(itemList);
    }

    @NonNull
    @Override
    public MarketAdapter.MarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent, false);
        return new MarketAdapter.MarketViewHolder(v, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onBindViewHolder(@NonNull MarketAdapter.MarketViewHolder holder, int position) {

        Item currentItem = items.get(position);

        String owner = "Owner: " + currentItem.getUsername();
        holder.imageView.setImageBitmap(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getName());
        holder.textView2.setText(currentItem.getDescription());
        holder.textView3.setText(owner);
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
//            ItemSuggestion itemSuggestion = new ItemSuggestion((RegularUser) MainActivity.getUser());
//            itemList = itemSuggestion.marketSuggestion(ud);

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
