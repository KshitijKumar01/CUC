package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Steptwo extends Fragment {

    EditText otp1,otp2,otp3,otp4;
    CardView views,verifyotpbtn;
    boolean opened;

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
        views = view.findViewById(R.id.otpbox);
        views.setVisibility(View.INVISIBLE);
        verifyotpbtn=view.findViewById(R.id.verifynumber);
        verifyotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmenu();
            }
        });

        EditText[] edit = {(EditText) otp1, (EditText) otp2,(EditText) otp3, (EditText) otp4};

        otp1.addTextChangedListener(new textwatcher(otp1, edit));
        otp2.addTextChangedListener(new textwatcher(otp2, edit));
        otp3.addTextChangedListener(new textwatcher(otp3, edit));
        otp4.addTextChangedListener(new textwatcher(otp4, edit));



        return  view;
    }
    private void openmenu() {
        if(!opened){
            views.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(
                    -views.getWidth(),
                   0,
                    0,
                    0);
            animate.setDuration(1000);
            animate.setFillAfter(true);
            views.startAnimation(animate);
        } else {
            views.setVisibility(View.INVISIBLE);
            TranslateAnimation animate = new TranslateAnimation(
                   0,
                    -views.getWidth(),
                    0,
                   0);
            animate.setDuration(1000);
            animate.setFillAfter(true);
            views.startAnimation(animate);
        }
        opened = !opened;
    }


}