package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class companion_adapter extends RecyclerView.Adapter<companion_adapter.Holder> {
    Context context;
    ArrayList<companion_request_post_data> arrcompanion;

    public companion_adapter(Context context, ArrayList<companion_request_post_data> arrcompanion) {
        this.context=context;
        this.arrcompanion=arrcompanion;
    }

    @NonNull
    @Override
    public companion_adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.companion_layout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull companion_adapter.Holder holder, int position) {

        holder.date.setText(arrcompanion.get(position).getDate());
        holder.from.setText(arrcompanion.get(position).getFrom());
        holder.to.setText(arrcompanion.get(position).getTo());
        holder.uid.setText(arrcompanion.get(position).getUid());
        holder.text.setText(arrcompanion.get(position).getText_message());
        holder.posted_on.setText(arrcompanion.get(position).getPosted_on());
        try
        {
            int x=arrcompanion.get(position).getElement();
            if(x==0)
            {
                holder.profileimg.setImageResource(R.mipmap.fire);
            }
            else if(x==1)
            {
                holder.profileimg.setImageResource(R.mipmap.water);
            }
            else if(x==2)
            {
                holder.profileimg.setImageResource(R.mipmap.air);
            }
            else if(x==3)
            {
                holder.profileimg.setImageResource(R.mipmap.earth);
            }
            else if(x==4)
            {
                holder.profileimg.setImageResource(R.mipmap.spirit);
            }
            else
            {
                holder.profileimg.setImageResource(R.mipmap.logo);
            }

        }
        catch (Exception e)
        {

        }




    }

    @Override
    public int getItemCount() {
        return arrcompanion.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView posted_on,uid,from,to,text,date;
        ImageView profileimg;
        public Holder(@NonNull View itemView) {
            super(itemView);
            posted_on=itemView.findViewById(R.id.posted_on);
            uid=itemView.findViewById(R.id.uid);
            from=itemView.findViewById(R.id.from);
            to=itemView.findViewById(R.id.to);
            text=itemView.findViewById(R.id.text);
            date=itemView.findViewById(R.id.date);
            profileimg=itemView.findViewById(R.id.profileimg);
        }
    }
}
