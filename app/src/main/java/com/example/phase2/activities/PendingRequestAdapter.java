package com.example.phase2.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.example.phase2.entity.TransactionRequest;

import java.util.ArrayList;

public class PendingRequestAdapter extends RecyclerView.Adapter<PendingRequestAdapter.PendingViewHolder> {

    private ArrayList<TransactionRequest> requests;
    private PendingRequestAdapter.OnItemClickListener mListener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDenyClick(int position);
    }

    public void setOnItemClickListener(PendingRequestAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class PendingViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView imageIcon;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;

        public PendingViewHolder(@NonNull View itemView, final PendingRequestAdapter.OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview1);
            textView1 = itemView.findViewById(R.id.textViewLine1);
            textView2 = itemView.findViewById(R.id.textViewLine2);
            textView3 = itemView.findViewById(R.id.textViewLine3);
            imageIcon = itemView.findViewById(R.id.smart_item_add_button_image);

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
                            listener.onDenyClick(position);
                        }
                    }
                }
            });
        }
    }

    public PendingRequestAdapter(ArrayList<TransactionRequest> itemList, Context current) {
        requests = itemList;
        this.context = current;
    }

    @NonNull
    @Override
    public PendingRequestAdapter.PendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent, false);
        return new PendingRequestAdapter.PendingViewHolder(v, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull PendingRequestAdapter.PendingViewHolder holder, int position) {

        TransactionRequest currentRequest = requests.get(position);
        boolean isTwoWay = currentRequest.getIsTwoWay();
        boolean isTemporary = currentRequest.getIsTemporary();

        final int resourceId = context.getResources().getIdentifier("deny_icon", "drawable", context.getPackageName());
        Drawable denyIcon = context.getDrawable(resourceId);

        if (isTwoWay && isTemporary) {
            String tradeType = "Two Way Temporary Trade";
            holder.textView3.setText(tradeType);
            Item itemToDisplay = currentRequest.getItemsToTrade().get(0);
            holder.imageView.setImageBitmap(itemToDisplay.getImageResource());
        }
        else if (isTwoWay && (!isTemporary)) {
            String tradeType = "Two Way Permanent Trade";
            holder.textView3.setText(tradeType);
            Item itemToDisplay = currentRequest.getItemsToTrade().get(0);
            holder.imageView.setImageBitmap(itemToDisplay.getImageResource());
        }
        else if (!(isTwoWay && isTemporary)) {
            String tradeType = "One Way Permanent Trade";
            holder.textView3.setText(tradeType);
            Item itemToDisplay = currentRequest.getItemsToTrade().get(0);
            holder.imageView.setImageBitmap(itemToDisplay.getImageResource());
        }
        else {
            String tradeType = "One Way Temporary Trade";
            holder.textView3.setText(tradeType);
            Item itemToDisplay = currentRequest.getItemsToTrade().get(0);
            holder.imageView.setImageBitmap(itemToDisplay.getImageResource());
        }

        String a = "Sent By";
        holder.textView1.setText(a);
        holder.textView2.setText(currentRequest.getOwner().getUsername());
        holder.imageIcon.setBackground(denyIcon);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
}
