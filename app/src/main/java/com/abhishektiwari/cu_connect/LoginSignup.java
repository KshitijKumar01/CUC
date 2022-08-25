package com.abhishektiwari.cu_connect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

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
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.logsincon,new Login()).commit();
        loginbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.green)));


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.green)));
                signupbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
                fragmentManager.beginTransaction().replace(R.id.logsincon,new Login()).commit();

            }
        });
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
                signupbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.green)));
                fragmentManager.beginTransaction().replace(R.id.logsincon,new Sign_up()).commit();

            }
        });


    }


}