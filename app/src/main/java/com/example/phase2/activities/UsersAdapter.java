package com.example.phase2.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.phase2.database.SaveData;
import com.example.phase2.entity.OneWayTrade;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.entity.Transaction;
import com.example.phase2.entity.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.SuspiciousViewHolder> implements Filterable {

    private ArrayList<RegularUser> users;
    private ArrayList<RegularUser> usersFull;
    private UsersAdapter.OnItemClickListener mListener;
    private Context context;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onFreezeClick(int position);
    }

    public void setOnItemClickListener(UsersAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class SuspiciousViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;
        public ImageView imageButton;

        public SuspiciousViewHolder(@NonNull View itemView, final UsersAdapter.OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview1);
            imageButton = itemView.findViewById(R.id.smart_item_add_button_image);
            textView1 = itemView.findViewById(R.id.textViewLine1);
            textView2 = itemView.findViewById(R.id.textViewLine2);
            textView3 = itemView.findViewById(R.id.textViewLine3);
            textView4 = itemView.findViewById(R.id.textViewLine4);

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

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onFreezeClick(position);
                        }
                    }
                }
            });
        }
    }

    public UsersAdapter(ArrayList<RegularUser> userList, Context current) {
        users = userList;
        usersFull = new ArrayList<>(userList);
        this.context = current;
    }

    @NonNull
    @Override
    public UsersAdapter.SuspiciousViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent, false);
        return new UsersAdapter.SuspiciousViewHolder(v, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.SuspiciousViewHolder holder, int position) {

        boolean unfreezeClicked = AdminMenuActivity.isUnfreezeClicked();
        RegularUser currentUser = users.get(position);
        ArrayList<Transaction> transactions = currentUser.getTransactions();
        final int resourceId = context.getResources().getIdentifier("freeze_icon", "drawable", context.getPackageName());
        Drawable freezeIcon = context.getDrawable(resourceId);
        File file = new File(SaveData.context.getFilesDir().getPath() + "/item_image/" + "default_image.png");
        Bitmap a = BitmapFactory.decodeFile(file.getAbsolutePath());

        int incompleteCounter = 0;
        int counterLend = 0;
        int counterBorrow = 0;

        for (Transaction transaction: transactions){
            if(!transaction.getTransactionCompleteness()){
                incompleteCounter += 1;
            }

            if (transaction.getTransactionType().equals("OneWay Permanent") || transaction.getTransactionType().equals("OneWay Temporary")){
                if (((OneWayTrade) transaction).getLenderName().equals(currentUser.getUsername())){
                    counterLend += 1;
                } else {
                    counterBorrow += 1;
                }
            }
            else{
                counterLend += 1;
                counterBorrow += 1;
            }
        }
        if (unfreezeClicked) {
            String unfreeze = "Unfreeze Request : " + currentUser.getUnfreezeRequest();
            holder.textView4.setText(unfreeze);
        }
        else {
            String incomplete = "Incomplete Transactions : " + incompleteCounter;
            holder.textView4.setText(incomplete);
        }
        String name = currentUser.getUsername();
        String lend = "Current Lend : " + counterLend;
        String borrow = "Current Borrow : " + counterBorrow;
        holder.textView3.setText(name);
        holder.textView1.setText(lend);
        holder.textView2.setText(borrow);
        holder.imageButton.setBackground(freezeIcon);
        holder.imageView.setImageBitmap(a);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public Filter getFilter() {
        return userFilter;
    }

    private Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(usersFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (RegularUser u : usersFull) {
                    if (u.getUsername().toLowerCase().contains(filterPattern)) {
                        filteredList.add(u);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {

            users.clear();
            users.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
