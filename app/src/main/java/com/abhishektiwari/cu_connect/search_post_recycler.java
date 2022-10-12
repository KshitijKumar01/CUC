package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class search_post_recycler extends RecyclerView.Adapter<search_post_recycler.Holder> {
    Context context;
    ArrayList<search_post_data> search_post_data;

    public search_post_recycler(Context context, ArrayList<search_post_data> search_post_data) {
        this.context=context;
        this.search_post_data= search_post_data;
    }

    @NonNull
    @Override
    public search_post_recycler.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.posts,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull search_post_recycler.Holder holder, int position) {


        if(!search_post_data.get(position).getImageurl().equals("null"))
        {
            Picasso.get()
                    .load(String.valueOf(search_post_data.get(position).getImageurl()))
                    .into(holder.postimg);
        }
        else {
            holder.postimg.setVisibility(View.GONE);
        }
        holder.posttext.setText(search_post_data.get(position).getPost_text());
        holder.postdate.setText(search_post_data.get(position).getDate());
        holder.postby.setText("by"+" "+search_post_data.get(position).getUid());
        holder.postlikestext.setText(String.valueOf(search_post_data.get(position).getLike()));

    }

    @Override
    public int getItemCount() {
        return search_post_data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView postimg;
        TextView posttext,postlikestext,postdate,postby;
        public Holder(@NonNull View itemView) {
            super(itemView);
            postlikestext=itemView.findViewById(R.id.postliketext);
            postimg=itemView.findViewById(R.id.postimg);
            posttext=itemView.findViewById(R.id.posttext);
            postdate=itemView.findViewById(R.id.date);
            postby=itemView.findViewById(R.id.postby);

        }
    }
}
