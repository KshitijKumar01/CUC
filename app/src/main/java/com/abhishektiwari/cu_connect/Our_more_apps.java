package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class Our_more_apps extends Fragment {

    WebView webView;
    public Our_more_apps() {
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
        View view= inflater.inflate(R.layout.fragment_our_more_apps, container, false);
        webView=view.findViewById(R.id.webView);
        webView.loadUrl("https://play.google.com/store/apps/collection/cluster?gsr=SkBqGHphVUxVYzVaUmRvcXA2RFVoQmNrU3c9PbICIwohCh1jb20uYWJoaXNoZWt0aXdhcmkuZGV2aWNlaW5mbxAH:S:ANO1ljLy1o8");
        return  view;
    }
}