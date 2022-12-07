package com.example.assignment3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.UsersViewHolder> {
    ArrayList<User> list;
    Context context;

    interface UsersClickListener {
        void onUserClicked(User selectedUser);
    }

    UsersRecyclerViewAdapter.UsersClickListener listener;

    public UsersRecyclerViewAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(context);
        View view = myInflater.inflate(R.layout.user_low_layout,parent,false);

        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.home_nickname.setText(list.get(position).nickName);
        holder.home_level.setText(Integer.toString(list.get(position).level));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView home_nickname;
        TextView home_level;
        public UsersViewHolder(View itemView) {
            super(itemView);
            home_nickname = itemView.findViewById(R.id.home_nickname);
            home_level = itemView.findViewById(R.id.home_level);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onUserClicked(list.get(getAdapterPosition()));
                }
            });
        }
    }
}
