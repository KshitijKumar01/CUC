package com.abhishektiwari.cu_connect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

public class LoginSignup extends AppCompatActivity{

    CardView loginbutton,signupbutton,one,two,three,four;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        loginbutton=findViewById(R.id.loginbutton);
        signupbutton=findViewById(R.id.signupbutton);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        loginbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.silver)));


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.redmain)));
                signupbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
            }
        });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
                signupbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.redmain)));
            }
        });

        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.logsincon,new LoginFragment()).commit();

    }


}