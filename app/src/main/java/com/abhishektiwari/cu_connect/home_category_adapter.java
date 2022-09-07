package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class home_category_adapter extends RecyclerView.Adapter<home_category_adapter.ViewHolder> {

    //vars
    private ArrayList<String> mCategories = new ArrayList<>();
    private Context mContext;

    public home_category_adapter(Context context, ArrayList<String> categories){
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

    }

    @Override
    public int getItemCount() {
//        return mCategories.size();
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView category;
        public ViewHolder(View itemView){
            super(itemView);
            category = itemView.findViewById(R.id.tv_category);

        }
    }
}
