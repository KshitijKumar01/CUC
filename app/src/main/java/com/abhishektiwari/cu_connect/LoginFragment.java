package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.RemoteCallbackList;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LoginFragment extends Fragment {

    Button btnRegister;
    static int active;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        active=1;


        return view;
    }


}