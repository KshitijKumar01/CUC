package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Profile extends Fragment {

    RecyclerView posts_recycler;
    profile_myposts_recycler posts_adapter;
    ArrayList<profile_myposts_data> dataarray;
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
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        posts_recycler=view.findViewById(R.id.mypostsrecycler);
        posts_recycler.setHasFixedSize(true);
        posts_recycler.setLayoutManager(new GridLayoutManager(getContext(),2));
        dataarray=new ArrayList<>();
        posts_adapter= new profile_myposts_recycler(getContext(),dataarray);

        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    profile_myposts_data studentDetails = dataSnapshot.getValue(profile_myposts_data.class);

                    dataarray.add(studentDetails);
                }
                posts_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


        posts_recycler.setAdapter(posts_adapter);

        return view;
    }
}