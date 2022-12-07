package com.example.assignment3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoriesRecyclerViewAdapter extends RecyclerView.Adapter<HistoriesRecyclerViewAdapter.HistoriesViewHolder> {

    ArrayList<History> list;
    Context context;

    interface HistoriesClickListener {
        void onHistoryClicked(History selectedHistory);
    }

    HistoriesClickListener listener;

    public HistoriesRecyclerViewAdapter(ArrayList<History> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(context);
        View view = myInflater.inflate(R.layout.history_low_layout,parent,false);

        return new HistoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriesViewHolder holder, int position) {
        holder.tradeDate.setText(list.get(position).tradeDate);
        holder.spid.setText(Integer.toString(list.get(position).spid));
        holder.grade.setText("+" + Integer.toString(list.get(position).grade));
        holder.bp.setText(Long.toString(list.get(position).value) + " BP");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HistoriesViewHolder extends RecyclerView.ViewHolder {
        TextView tradeDate;
        TextView spid;
        TextView grade;
        TextView bp;
        public HistoriesViewHolder(View itemView) {
            super(itemView);
            tradeDate = itemView.findViewById(R.id.tradeDate);
            spid = itemView.findViewById(R.id.spid);
            grade = itemView.findViewById(R.id.grade);
            bp = itemView.findViewById(R.id.bp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onHistoryClicked(list.get(getAdapterPosition()));
                }
            });
        }
    }
}
