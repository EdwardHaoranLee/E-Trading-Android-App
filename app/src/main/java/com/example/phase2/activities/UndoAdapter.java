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
import com.example.phase2.controller.UndoRegularUser;
import com.example.phase2.database.SaveData;
import com.example.phase2.entity.RegularUser;
import com.example.phase2.entity.User;
import com.example.phase2.gateway.UserData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UndoAdapter extends RecyclerView.Adapter<UndoAdapter.UndoViewHolder> implements Filterable {

    private ArrayList<RegularUser> users;
    private ArrayList<RegularUser> usersFull;
    private UndoAdapter.OnItemClickListener mListener;
    private Context context;
    private UserData ud = MainActivity.getUserData();


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onUndoClick(int position);
    }

    public void setOnItemClickListener(UndoAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class UndoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public ImageView imageButton;

        public UndoViewHolder(@NonNull View itemView, final UndoAdapter.OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview1);
            imageButton = itemView.findViewById(R.id.smart_item_add_button_image);
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

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onUndoClick(position);
                        }
                    }
                }
            });
        }
    }

    public UndoAdapter(ArrayList<RegularUser> userList, Context current) {
        users = userList;
        usersFull = new ArrayList<>(userList);
        this.context = current;
    }

    @NonNull
    @Override
    public UndoAdapter.UndoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent, false);
        return new UndoAdapter.UndoViewHolder(v, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onBindViewHolder(@NonNull UndoAdapter.UndoViewHolder holder, int position) {

        RegularUser currentUser = users.get(position);
        String latestAction = UndoRegularUser.peekUndoInfo(ud, currentUser);
        String latest = "Latest Undoable Action :";
        String name = currentUser.getUsername();
        final int resourceId = context.getResources().getIdentifier("undo_icon", "drawable", context.getPackageName());
        Drawable undoIcon = context.getDrawable(resourceId);
        File file = new File(SaveData.context.getFilesDir().getPath() + "/item_image/" + "default_image.png");
        Bitmap a = BitmapFactory.decodeFile(file.getAbsolutePath());

        holder.textView3.setText(name);
        holder.textView1.setText(latest);
        holder.textView2.setText(latestAction);
        holder.imageButton.setBackground(undoIcon);
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
