package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class search_result_recycler extends RecyclerView.Adapter<search_result_recycler.Holder> {
    Context context;
    ArrayList<profile_search_data> array;


    public search_result_recycler(Context context,ArrayList<profile_search_data> array) {
        this.context=context;
        this.array=array;
    }

    @NonNull
    @Override
    public search_result_recycler.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_profile,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull search_result_recycler.Holder holder, int position) {
        holder.uid.setText(array.get(position).getUid());
        holder.following.setText(String.valueOf(array.get(position).getFollowing()));
        holder.followers.setText(String.valueOf(array.get(position).getFollowers()));

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView followers,following,uid;
        public Holder(@NonNull View itemView) {
            super(itemView);
            followers=itemView.findViewById(R.id.followers);
            following=itemView.findViewById(R.id.afollowings);
            uid=itemView.findViewById(R.id.uid);
        }
    }
}
