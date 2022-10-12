package com.abhishektiwari.cu_connect;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class saved_posts extends Fragment {

    RecyclerView posts_recycler;
    ArrayList<search_post_data> search_postd;
    search_post_recycler POST_ADAPTER;
    CardView loading;

    public saved_posts() {
        // Required empty public constructor
    }


    public static saved_posts newInstance(String param1, String param2) {
        saved_posts fragment = new saved_posts();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_saved_posts, container, false);
        loading=view.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        posts_recycler=view.findViewById(R.id.recycler_Posts_Search);
        posts_recycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        posts_recycler.setHasFixedSize(true);
        search_postd =new ArrayList<>();
        posts_recycler.getRecycledViewPool().setMaxRecycledViews(0, 0);

        POST_ADAPTER =new search_post_recycler(getContext(),search_postd);
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("Saved Posts").getChildren())
                {
                    search_post_data search_post_data=dataSnapshot.getValue(search_post_data.class);
                    search_postd.add(search_post_data);


                }
                POST_ADAPTER.notifyDataSetChanged();
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        posts_recycler.setAdapter(POST_ADAPTER);
        return  view;
    }
}