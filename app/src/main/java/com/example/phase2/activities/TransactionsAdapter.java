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
import com.example.phase2.entity.OneWayTrade;
import com.example.phase2.entity.Transaction;
import com.example.phase2.entity.TwoWayTrade;

import java.util.ArrayList;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> implements Filterable{

    private ArrayList<Transaction> transactions;
    private ArrayList<Transaction> transactionsFull;
    private TransactionsAdapter.OnItemClickListener mListener;
    private Context context;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onConfirmClick(int position);
    }

    public void setOnItemClickListener(TransactionsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView imageIcon;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;

        public TransactionViewHolder(@NonNull View itemView, final TransactionsAdapter.OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview1);
            imageIcon = itemView.findViewById(R.id.smart_item_add_button_image);
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

            imageIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onConfirmClick(position);
                        }
                    }
                }
            });
        }
    }

    public TransactionsAdapter(ArrayList<Transaction> transactionsList, Context current) {
        transactions = transactionsList;
        transactionsFull = new ArrayList<>(transactionsList);
        this.context = current;
    }

    @NonNull
    @Override
    public TransactionsAdapter.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent, false);
        return new TransactionsAdapter.TransactionViewHolder(v, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull TransactionsAdapter.TransactionViewHolder holder, int position) {

        Transaction currentTransaction = transactions.get(position);
        String type = currentTransaction.getTransactionType();
        String complete = "";
        String due = "Due On:";

        switch (type) {
            case "TwoWay Temporary":
                TwoWayTrade t = (TwoWayTrade) currentTransaction;
                Item item = t.getBorrowerItem();
                holder.imageView.setImageBitmap(item.getImageResource());
                holder.textView1.setText(due);
                holder.textView2.setText(currentTransaction.getDueDate().toString());
                holder.textView3.setText(type);
                break;
            case "OneWay Permanent":
                OneWayTrade t2 = (OneWayTrade) currentTransaction;
                Item item2 = t2.getItem();
                holder.imageView.setImageBitmap(item2.getImageResource());
                holder.textView3.setText(type);
                holder.textView4.setText(complete);

                break;
            case "TwoWay Permanent":
                TwoWayTrade t1 = (TwoWayTrade) currentTransaction;
                Item item1 = t1.getBorrowerItem();
                holder.imageView.setImageBitmap(item1.getImageResource());
                holder.textView3.setText(type);

                break;
            default:
                OneWayTrade t3 = (OneWayTrade) currentTransaction;
                Item item3 = t3.getItem();
                holder.imageView.setImageBitmap(item3.getImageResource());
                holder.textView1.setText(due);
                holder.textView2.setText(currentTransaction.getDueDate().toString());
                holder.textView3.setText(type);

                break;
        }
        if (!currentTransaction.getIsComplete() && currentTransaction.getConfirmationNum() == 0) {
            complete = "Waiting for Confirmation";
        }
        else if (!currentTransaction.getIsComplete() && currentTransaction.getConfirmationNum() == 1) {
            complete = "Still Waiting for Another Confirmation";
        }
        else if (currentTransaction.getIsComplete() && currentTransaction.getConfirmationNum() == 2) {
            complete = "Transaction Completed";
        }
        holder.textView4.setText(complete);

        final int resourceId = context.getResources().getIdentifier("check_icon", "drawable", context.getPackageName());
        Drawable checkIcon = context.getDrawable(resourceId);

        holder.imageIcon.setImageDrawable(checkIcon);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    @Override
    public Filter getFilter() {
        return transactionFilter;
    }

    private Filter transactionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Transaction> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(transactionsFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Transaction transaction : transactionsFull) {
                    if (transaction.getCreatedDate().toString().contains(filterPattern)) {
                        filteredList.add(transaction);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {

            transactions.clear();
            transactions.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
