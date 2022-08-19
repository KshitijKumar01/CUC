package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class Search extends Fragment {


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
        RelativeLayout item_menu_click_parent = view.findViewById(R.id.item_menu_click_parent);

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

        item_mic_click_parent.setOnClickListener(v -> Toast.makeText(getContext(), "Mic clicked", Toast.LENGTH_SHORT).show());
        item_menu_click_parent.setOnClickListener(v -> Toast.makeText(getContext(), "Menu clicked", Toast.LENGTH_SHORT).show());
        item_clear_click_parent.setOnClickListener(v -> item_search_input.getText().clear());

        return view;
    }
}