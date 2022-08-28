package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class Steptwo extends Fragment {

    EditText otp1,otp2,otp3,otp4;


    public Steptwo() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_steptwo, container, false);
        otp1=view.findViewById(R.id.otp1);
        otp2=view.findViewById(R.id.otp2);
        otp3=view.findViewById(R.id.otp3);
        otp4=view.findViewById(R.id.otp4);

        EditText[] edit = {(EditText) otp1, (EditText) otp2,(EditText) otp3, (EditText) otp4};

        otp1.addTextChangedListener(new textwatcher(otp1, edit));
        otp2.addTextChangedListener(new textwatcher(otp2, edit));
        otp3.addTextChangedListener(new textwatcher(otp3, edit));
        otp4.addTextChangedListener(new textwatcher(otp4, edit));



        return  view;
    }
}