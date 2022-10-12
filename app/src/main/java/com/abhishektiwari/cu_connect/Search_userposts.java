package com.abhishektiwari.cu_connect;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Search_userposts extends Fragment {

   RecyclerView posts_recycler;
   ArrayList<search_post_data> search_postd;
   search_post_recycler POST_ADAPTER;

    public Search_userposts() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search_userposts, container, false);
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
                Toast.makeText(getContext(), Search.search_uid, Toast.LENGTH_SHORT).show();
                for(DataSnapshot dataSnapshot : snapshot.child("Profile_Search_Post").child(Search.search_uid).getChildren())
                {
                    Toast.makeText(getContext(), "dataadded", Toast.LENGTH_SHORT).show();
                    search_post_data search_post_data=dataSnapshot.getValue(search_post_data.class);
                    search_postd.add(search_post_data);


                }
                POST_ADAPTER.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        posts_recycler.setAdapter(POST_ADAPTER);

        return  view;
    }
}