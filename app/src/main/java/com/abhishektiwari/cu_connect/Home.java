package com.abhishektiwari.cu_connect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Home extends Fragment  {

    RecyclerView posts_recycler, top_view_recycler;
    home_posts_recycler posts_adapter;
    home_category_adapter category_adapter;
    ImageView addpost;
    String uid;
    String first;
    LottieAnimationView lottieAnimationView;
    ArrayList<home_post_data> arrhome;
    ArrayList<String> arr;
    CardView loading;
    public Home() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        loading=view.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        arr = new ArrayList<>();
        arrhome=new ArrayList<>();
        posts_recycler = view.findViewById(R.id.postsrecycler);
        posts_recycler.setHasFixedSize(true);
        uid= FirebaseAuth.getInstance().getUid();
        posts_recycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        posts_adapter = new home_posts_recycler(getContext(),arrhome);
        posts_recycler.setAdapter(posts_adapter);
        posts_recycler.getRecycledViewPool().setMaxRecycledViews(0, 0);
        addpost = view.findViewById(R.id.addpost);
        lottieAnimationView=view.findViewById(R.id.loadinganimation);
        lottieAnimationView.setVisibility(View.VISIBLE);
        top_view_recycler = view.findViewById(R.id.categoryRecycler);
        top_view_recycler.setHasFixedSize(true);
        top_view_recycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        category_adapter = new home_category_adapter(getContext(), arr, new home_category_adapter.OnItemClickListener() {

            public void onItemClick(String item) {
                try {
                    updateRecycler(item);
                }
                catch (Exception e)
                {

                }
            }
        });

        top_view_recycler.setAdapter(category_adapter);

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Post_for_home.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot dataSnapshot : snapshot.child("users").child(uid).child("Intrests").getChildren()) {

                        arr.add(dataSnapshot.getValue(String.class));
                    }

                }catch (Exception e)
                {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                lottieAnimationView.setVisibility(View.INVISIBLE);
                category_adapter.notifyDataSetChanged();
                first=arr.get(0);
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                arr.add("Error happened");
                category_adapter.notifyDataSetChanged();
                lottieAnimationView.setVisibility(View.INVISIBLE);

            }
        });
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.child(first).getChildren()) {

                    home_post_data home_post_data=dataSnapshot.getValue(home_post_data.class);
                    arrhome.add(home_post_data);
                }

                lottieAnimationView.setVisibility(View.INVISIBLE);
                posts_adapter.notifyDataSetChanged();
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    private void updateRecycler(String item) {
        arrhome.clear();
        loading.setVisibility(View.VISIBLE);


        try {
            {
                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.child(item).getChildren()) {

                            home_post_data home_post_data=dataSnapshot.getValue(home_post_data.class);
                            arrhome.add(home_post_data);
                        }

                        loading.setVisibility(View.INVISIBLE);
                        posts_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        loading.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }

        }
        catch (Exception e)
        {}
    }


}