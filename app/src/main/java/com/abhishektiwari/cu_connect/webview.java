package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.WeakHashMap;

public class webview extends Fragment {


    WebView webView;

    public webview() {
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
        View view= inflater.inflate(R.layout.fragment_webview, container, false);
        webView=view.findViewById(R.id.webview);
        webView.loadUrl("https://play.google.com/store/apps/collection/cluster?gsr=SkBqGHphVUxVYzVaUmRvcXA2RFVoQmNrU3c9PbICIwohCh1jb20uYWJoaXNoZWt0aXdhcmkuZGV2aWNlaW5mbxAH:S:ANO1ljLy1o8");

        webView.setWebViewClient(new WebViewClient());
        return  view;
    }
}