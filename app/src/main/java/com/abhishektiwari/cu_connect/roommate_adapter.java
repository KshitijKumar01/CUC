package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class roommate_adapter extends RecyclerView.Adapter<roommate_adapter.Holder> {
    Context context;


    public roommate_adapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public roommate_adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.roomamtes,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull roommate_adapter.Holder holder, int position) {

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
