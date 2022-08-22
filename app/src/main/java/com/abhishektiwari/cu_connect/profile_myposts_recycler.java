package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class profile_myposts_recycler extends RecyclerView.Adapter<profile_myposts_recycler.Holder> {
    Context context;

    public profile_myposts_recycler(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public profile_myposts_recycler.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.myposts_profile,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull profile_myposts_recycler.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
