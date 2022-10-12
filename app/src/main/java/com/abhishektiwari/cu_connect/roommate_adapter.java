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

public class roommate_adapter extends RecyclerView.Adapter<roommate_adapter.Holder> {
    Context context;
    ArrayList<Roommate_request_post_data> arrroommates;

    public roommate_adapter(Context context, ArrayList<Roommate_request_post_data> arrroommates) {
        this.context=context;
        this.arrroommates=arrroommates;
    }

    @NonNull
    @Override
    public roommate_adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.roomamtes,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull roommate_adapter.Holder holder, int position) {
        holder.uid.setText(arrroommates.get(position).getUid());
        holder.text.setText(arrroommates.get(position).getText_message());
        holder.date.setText(arrroommates.get(position).getDate());
        holder.in.setText(arrroommates.get(position).getIn());
        holder.persons.setText(arrroommates.get(position).getPersons());
        try
        {
            int x=arrroommates.get(position).getElement();
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
        return arrroommates.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView uid,date,text,in,persons;
        ImageView profileimg;
        public Holder(@NonNull View itemView) {
            super(itemView);
            uid=itemView.findViewById(R.id.uidr);
            date=itemView.findViewById(R.id.dater);
            text=itemView.findViewById(R.id.textr);
            in=itemView.findViewById(R.id.inr);
            persons=itemView.findViewById(R.id.personsr);
            profileimg=itemView.findViewById(R.id.profileimg);
        }
    }
}
