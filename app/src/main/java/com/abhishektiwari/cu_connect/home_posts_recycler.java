package com.abhishektiwari.cu_connect;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class home_posts_recycler extends RecyclerView.Adapter<home_posts_recycler.Holder> {
    Context context;

    public home_posts_recycler(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public home_posts_recycler.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.posts,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull home_posts_recycler.Holder holder, int position) {

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
