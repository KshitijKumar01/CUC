package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.firebase.database.FirebaseDatabase;


public class stepfour extends Fragment {
    SharedPreferences sharedpreferences;
    stepsindicator s;
    String uid;
    String Email,Password;
    public stepfour() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_stepfour, container, false);
        sharedpreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("step", String.valueOf(4));
        uid=sharedpreferences.getString("Uid",null);
        Email=sharedpreferences.getString("Email",null);
        Password=sharedpreferences.getString("Password",null);
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Steps Completed").setValue(4);

        s=new stepsindicator();
        s.setI(4);
        return  view;
    }
}