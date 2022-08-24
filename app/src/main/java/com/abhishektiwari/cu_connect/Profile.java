package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Profile extends Fragment {

    RecyclerView posts_recycler;
    profile_myposts_recycler posts_adapter;
    public Profile() {
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
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        posts_recycler=view.findViewById(R.id.mypostsrecycler);
        posts_recycler.setHasFixedSize(true);
        posts_recycler.setLayoutManager(new GridLayoutManager(getContext(),2));

        posts_adapter= new profile_myposts_recycler(getContext());
        posts_recycler.setAdapter(posts_adapter);

        return view;
    }
}