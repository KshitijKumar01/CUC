package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Search extends Fragment {
    RecyclerView recyclerView;
    search_result_recycler searchadapter;

    ArrayList<profile_search_data> array;
    String uid="";
    Integer following=0,followers=0;
    long phoneno=0;
    public Search() {
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
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        EditText item_search_input = view.findViewById(R.id.item_search_input);
        RelativeLayout item_clear_click_parent = view.findViewById(R.id.item_clear_click_parent);
        RelativeLayout item_mic_click_parent = view.findViewById(R.id.item_mic_click_parent);

        array=new ArrayList<>();
        recyclerView=view.findViewById(R.id.searchrecycler);
        searchadapter=new search_result_recycler(getContext(),array);
        recyclerView.setAdapter(searchadapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        item_search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (item_search_input.length() > 0) {
                    if (item_clear_click_parent.getVisibility() != View.VISIBLE) {
                        item_clear_click_parent.setVisibility(View.VISIBLE);
                    }
                } else {
                    item_clear_click_parent.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        item_mic_click_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Searchtext=item_search_input.getText().toString();

                uid =Searchtext;
                Toast.makeText(getContext(), item_search_input.getText().toString(), Toast.LENGTH_SHORT).show();
                if(!Searchtext.isEmpty())
                {
                    try {
                        FirebaseDatabase.getInstance().getReference().child("Profile_Search_Post").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Toast.makeText(getContext(), "Snapshot", Toast.LENGTH_SHORT).show();
                                if(snapshot.child(Searchtext).exists()) {
                                    array.clear();
                                    Toast.makeText(getContext(), "has Child", Toast.LENGTH_SHORT).show();
                                    FirebaseDatabase.getInstance().getReference().child("Profile info").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            try {
                                                following = snapshot.child("Following").getValue(Integer.class);
                                                followers = snapshot.child("Followers").getValue(Integer.class);
                                                phoneno = snapshot.child("Phone No").getValue(Long.class);
                                                profile_search_data pf = new profile_search_data(uid, followers, following, phoneno);
                                                array.add(pf);
                                                searchadapter.notifyDataSetChanged();

                                            } catch (Exception e) {
                                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }

                                else
                                {
                                    Toast.makeText(getContext(), "has not child", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

              else
                {
                    Toast.makeText(getContext(), "is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        item_clear_click_parent.setOnClickListener(v -> item_search_input.getText().clear());


        return view;
    }
}