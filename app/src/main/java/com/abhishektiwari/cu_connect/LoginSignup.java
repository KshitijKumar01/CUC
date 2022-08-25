package com.abhishektiwari.cu_connect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginSignup extends AppCompatActivity{

    CardView loginbutton,signupbutton,one,two,three,four;
    TextView login,signup;
    LinearLayout view;
    boolean opened;
    int i=0;



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
        login=findViewById(R.id.logintext);
        signup=findViewById(R.id.signuptext);

        view = findViewById(R.id.up_downlayout);
        view.setVisibility(View.INVISIBLE);

        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.logsincon,new Login()).commit();
        loginbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.red)));
        login.setTextColor(getResources().getColor(R.color.white));
        signup.setTextColor(getResources().getColor(R.color.black));

        i=0;

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(i!=0)
                {
                    openmenu();
                }
                i=0;
                loginbutton.setClickable(false);
                signupbutton.setClickable(true);
                login.setTextColor(getResources().getColor(R.color.white));
                signup.setTextColor(getResources().getColor(R.color.black));
                loginbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.red)));
                signupbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
                fragmentManager.beginTransaction().replace(R.id.logsincon,new Login()).commit();

            }
        });
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0)
                {
                    openmenu();
                }

                i=1;
                loginbutton.setClickable(true);
                signupbutton.setClickable(false);
                login.setTextColor(getResources().getColor(R.color.black));
                signup.setTextColor(getResources().getColor(R.color.white));
                loginbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
                signupbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.red)));
                fragmentManager.beginTransaction().replace(R.id.logsincon,new Sign_up()).commit();

            }



        });


    }
    private void openmenu() {
        if(!opened){
            view.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(
                    0,
                    0,
                    view.getHeight(),
                    0);
            animate.setDuration(1000);
            animate.setFillAfter(true);
            view.startAnimation(animate);
        } else {
            view.setVisibility(View.INVISIBLE);
            TranslateAnimation animate = new TranslateAnimation(
                    0,
                    0,
                    0,
                    view.getHeight());
            animate.setDuration(1000);
            animate.setFillAfter(true);
            view.startAnimation(animate);
        }
        opened = !opened;
    }


}