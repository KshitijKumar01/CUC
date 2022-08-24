package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class search_result_recycler extends RecyclerView.Adapter<search_result_recycler.Holder> {
    Context context;


    public search_result_recycler(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public search_result_recycler.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_profile,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull search_result_recycler.Holder holder, int position) {

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
