package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Home extends Fragment {

    RecyclerView posts_recycler, top_view_recycler;
    home_posts_recycler posts_adapter;
    home_category_adapter category_adapter;


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
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        posts_recycler=view.findViewById(R.id.postsrecycler);
        posts_recycler.setHasFixedSize(true);
        posts_recycler.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        posts_adapter=new home_posts_recycler(getContext());
        posts_recycler.setAdapter(posts_adapter);


        ArrayList<String> arr =new ArrayList<>();
        arr.add("home");
        arr.add("home");
        arr.add("home");
        arr.add("home");
        arr.add("home");
        arr.add("home");

        top_view_recycler = view.findViewById(R.id.categoryRecycler);
        top_view_recycler.setHasFixedSize(true);
        top_view_recycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        category_adapter = new home_category_adapter(getContext(), arr);
        top_view_recycler.setAdapter(category_adapter);

        return  view;
    }
}