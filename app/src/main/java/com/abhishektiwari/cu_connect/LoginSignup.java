package com.abhishektiwari.cu_connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginSignup extends AppCompatActivity{

    CardView loginbutton,signupbutton,one,two,three,four;
    TextView login,signup;
    LinearLayout view;
    boolean opened;
    Integer steps=1;
    int i=0;
    FirebaseUser user;
    stepsindicator s;
    ToastClass t;

    @Override
    protected void onStart() {
        super.onStart();
        user=FirebaseAuth.getInstance().getCurrentUser();

        t=new ToastClass(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);



        getsteps();
        s=new stepsindicator();
        loginbutton=findViewById(R.id.loginbutton);
        signupbutton=findViewById(R.id.signupbutton);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                s.getI();
                indicatestep();
                handler.postDelayed(this, 1000);
            }
        });


        indicatestep();
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
        loginbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
        login.setTextColor(getResources().getColor(R.color.white));
        signup.setTextColor(getResources().getColor(R.color.black));
        i=0;

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    s.setI(1);
                    indicatestep();
                    fragmentManager.beginTransaction().setCustomAnimations(
                            // exit
                            R.anim.slide_up,   // popEnter
                            R.anim.slide_down  // popExit
                    ).replace(R.id.logsincon,new Sign_up()).commit();
                }catch(Exception e)
                {}
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(steps>=1)
                    {
                        s.setI(2);
                        indicatestep();
                        fragmentManager.beginTransaction().setCustomAnimations(
                                // exit
                                R.anim.slide_up,   // popEnter
                                R.anim.slide_down  // popExit
                        ).replace(R.id.logsincon,new Steptwo()).commit();
                    }
                    else
                    {
                        t.errortoast("complete previous steps first");                    }
                }catch(Exception e)
                {}
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(steps>=2){
                        s.setI(3);
                        indicatestep();
                        fragmentManager.beginTransaction().setCustomAnimations(
                                // exit
                                R.anim.slide_up,   // popEnter
                                R.anim.slide_down  // popExit
                        ).replace(R.id.logsincon,new stepthree()).commit();
                    }
                    else
                    {
                        t.errortoast("complete previous steps first");
                    }
                }catch (Exception e)
                {}
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(steps>=3) {
                        s.setI(4);
                        indicatestep();
                        fragmentManager.beginTransaction().setCustomAnimations(
                                // exit
                                R.anim.slide_up,   // popEnter
                                R.anim.slide_down  // popExit
                        ).replace(R.id.logsincon,new stepfour()).commit();
                    }
                    else
                    {
                        t.errortoast("complete previous steps first");                    }
                }catch (Exception e)
                {}
            }
        });

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
                loginbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                signupbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
                fragmentManager.beginTransaction().setCustomAnimations(
                        // exit
                        R.anim.slide_up,   // popEnter
                        R.anim.slide_down  // popExit
                ).replace(R.id.logsincon,new Login()).commit();

            }
        });
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0)
                {
                    openmenu();
                    one.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                    two.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    three.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    four.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));

                }

                i=1;
                loginbutton.setClickable(true);
                signupbutton.setClickable(false);
                login.setTextColor(getResources().getColor(R.color.black));
                signup.setTextColor(getResources().getColor(R.color.white));
                loginbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
                signupbutton.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                try {
                    fragmentManager.beginTransaction().setCustomAnimations(
                            // exit
                            R.anim.slide_up,   // popEnter
                            R.anim.slide_down  // popExit
                    ).replace(R.id.logsincon,new Sign_up()).commit();
                }catch (Exception e){}

            }



        });


    }

    private void getsteps() {
        if(user!=null)
        {
            FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    steps=snapshot.child("users").child(user.getUid()).child("Steps Completed").getValue(Integer.class);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    steps=1;

                }
            });

        }
        else
        {
            steps=1;
        }
    }

    private void indicatestep() {
        if(s.getI()==1)
        {
            one.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
            two.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
            three.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
            four.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));

        }
        else if(s.getI()==2)
        {
            one.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
            two.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
            three.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
            four.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));



        }
        else if(s.getI()==3)
        {
            one.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
            two.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
            three.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
            four.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));

        }
        else if(s.getI()==4)
        {
            one.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
            two.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
            three.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.white)));
            four.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.yellow)));



        }
        else {

        }

    }

    private void openmenu() {
        s.setI(1);
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