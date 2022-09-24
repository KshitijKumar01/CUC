package com.abhishektiwari.cu_connect;

import android.content.ContentValues;
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

public class home_posts_recycler extends RecyclerView.Adapter<home_posts_recycler.Holder> {
    Context context;
    ArrayList<home_post_data> home_post_d;

    public home_posts_recycler(Context context,ArrayList<home_post_data> home_post_d) {
        this.context=context;
        this.home_post_d=home_post_d;
    }

    @NonNull
    @Override
    public home_posts_recycler.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.posts,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull home_posts_recycler.Holder holder, int position) {

        if(!home_post_d.get(position).getImageurl().equals("null"))
        {
            Picasso.get()
                    .load(String.valueOf(home_post_d.get(position).getImageurl()))
                    .into(holder.postimg);
        }
        else {
            holder.postimg.setVisibility(View.GONE);
        }
        holder.posttext.setText(home_post_d.get(position).getPost_text());
        holder.postdate.setText(home_post_d.get(position).getDate());
        holder.postby.setText("by "+home_post_d.get(position).getUid());
        holder.postlikestext.setText(String.valueOf(home_post_d.get(position).getLike()));

    }

    @Override
    public int getItemCount() {
        return home_post_d.size();
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
