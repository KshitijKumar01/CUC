package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class roommate extends Fragment {


    RecyclerView recyclerView;
    roommate_adapter roommate_adapter;

    public roommate() {
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
        View view= inflater.inflate(R.layout.fragment_roommate, container, false);
        recyclerView=view.findViewById(R.id.roommates_recycler);
        roommate_adapter =new roommate_adapter(getContext());
        recyclerView.setAdapter(roommate_adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}