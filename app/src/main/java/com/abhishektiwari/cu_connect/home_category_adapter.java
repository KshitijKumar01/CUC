package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class home_category_adapter extends RecyclerView.Adapter<home_category_adapter.ViewHolder> {



    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    //vars
    private ArrayList<String> mCategories = new ArrayList<>();
    private Context context;
    private final OnItemClickListener listener;

    public home_category_adapter(Context context, ArrayList<String> categories,OnItemClickListener listener){
        this.context=context;
        this.listener=listener;
        mCategories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_top_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category.setText(mCategories.get(position));
        holder.bind(mCategories.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return mCategories.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView category;
        public ViewHolder(View itemView){
            super(itemView);
            category = itemView.findViewById(R.id.tv_category);

        }

        public void bind(String s,OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(s);
                }
            });
        }
    }
}
