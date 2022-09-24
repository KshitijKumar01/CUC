package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    String branch,semester,uid;
    Integer followers,following,likes;
    TextView Branchsem,email,followerst,likest,followingst;
    CardView uploading;
    SharedPreferences sharedpreferences;
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
        sharedpreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        Branchsem=view.findViewById(R.id.branchsem);
        email=view.findViewById(R.id.email);
        followingst=view.findViewById(R.id.following);
        followerst=view.findViewById(R.id.followers);
        likest=view.findViewById(R.id.likes);
        posts_recycler=view.findViewById(R.id.mypostsrecycler);
        uploading=view.findViewById(R.id.uploading);
        uploading.setVisibility(View.VISIBLE);

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
                uploading.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        posts_recycler.setAdapter(posts_adapter);


        getProfilevalues();
        return view;
    }

    private void getProfilevalues() {
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branch=snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("Branch").getValue(String.class);
                semester=snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("Semester").getValue(String.class);
                uid=snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("College UID").getValue(String.class);
                likes=snapshot.child("Profile info").child(uid).child("Likes").getValue(Integer.class);
                followers=snapshot.child("Profile info").child(uid).child("Followers").getValue(Integer.class);
                following=snapshot.child("Profile info").child(uid).child("Following").getValue(Integer.class);
                SetProfileText(branch,semester,uid,likes,followers,following);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SetProfileText(String branch, String semester, String uid, Integer likes, Integer followers, Integer following) {
        Branchsem.setText(branch+" , "+semester);
        email.setText(uid+"@cuchd.in");
        likest.setText(String.valueOf(likes));
        followerst.setText(String.valueOf(followers));
        followingst.setText(String.valueOf(following));
    }
}