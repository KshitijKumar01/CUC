package com.abhishektiwari.cu_connect;

import android.icu.text.CaseMap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Cuims_Login extends Fragment {


    TextView title;
    String uid;
    Content content;
    public Cuims_Login() {
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
        View view = inflater.inflate(R.layout.fragment_cuims__login, container, false);
        title=view.findViewById(R.id.title);
        content=new Content();
        content.execute();
        getProfilevalues();

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uid=snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("College UID").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }
    private void getProfilevalues() {
    }
    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
           Document doc = null;
            try {
                doc = Jsoup.connect("https://uims.cuchd.in/uims/Login.aspx").get();
                String l=doc.getElementById("pHeader").text();
                title.setText(doc.html());
            } catch (Exception e) {
                title.setText(e.getMessage());
            }
            Connection.Response response=null;
            try {
                String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36";
                String referer = "https://uims.cuchd.in/uims/";
                HashMap<String, String> postMap = new HashMap<String, String>();
                postMap.put("txtUserId","21bcs1041");

                Connection.Response loginForm = Jsoup.connect("https://uims.cuchd.in/uims/Login.aspx")
                        .method(Connection.Method.GET)
                        .execute();

                Map<String, String> loginCookies = loginForm.cookies();

                Document document = Jsoup
                        .connect("https://uims.cuchd.in/uims/Login.aspx")
                        .data(postMap)
                        .cookies(loginCookies)
                        .post();

                Document document2 = Jsoup.connect("https://uims.cuchd.in/uims/")
                        .cookies(loginCookies)
                        .ignoreContentType(true)
                        .get();

                System.out.println(document2.baseUri());
           //     title.setText(document2.baseUri());

            }
            catch (Exception e)
            {

            }




            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
        }
    }
}