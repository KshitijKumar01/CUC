package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class profile_myposts_recycler extends RecyclerView.Adapter<profile_myposts_recycler.Holder> {
    Context context;
    ArrayList<profile_myposts_data> array;

    public profile_myposts_recycler(Context context ,ArrayList<profile_myposts_data> array) {
        this.context=context;
        this.array=array;
    }

    @NonNull
    @Override
    public profile_myposts_recycler.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.myposts_profile,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull profile_myposts_recycler.Holder holder, int position) {

        if(array.get(position).getImageurl()!="null")
        {

            Picasso.get()
                    .load(String.valueOf(array.get(position).getImageurl()))
                    .into(holder.postimg);
        }
        if(array.get(position).getPost_text()!="null")
        {

            holder.posttext.setText(array.get(position).getPost_text());
        }

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView postimg;
        TextView posttext;
        public Holder(@NonNull View itemView) {
            super(itemView);
            postimg=itemView.findViewById(R.id.mypostsimg);
            posttext=itemView.findViewById(R.id.myposttext);

        }
    }
}
