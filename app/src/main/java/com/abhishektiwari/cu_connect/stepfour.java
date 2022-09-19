package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
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
    int i=0;
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
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if(i==1)
                {
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
                i++;
                handler.postDelayed(this, 5000);

            }
        });
        editor.putString("step", String.valueOf(4));
        editor.putString("logged in", "yes");
        uid=sharedpreferences.getString("Uid",null);
        Email=sharedpreferences.getString("Email",null);
        Password=sharedpreferences.getString("Password",null);
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Steps Completed").setValue(4);

        s=new stepsindicator();
        s.setI(4);
        return  view;
    }
}