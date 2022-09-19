package com.abhishektiwari.cu_connect;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.RemoteCallbackList;
import android.telecom.Call;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rejowan.cutetoast.CuteToast;

public class Login extends Fragment {
    ImageView seye;
    TextView loginbtn;
    LottieAnimationView loadinganimation;
    EditText semail,spassword;
    int i,steps;
    stepsindicator s;
    FirebaseUser user;

    ToastClass t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        semail=view.findViewById(R.id.lemail);
        loadinganimation=view.findViewById(R.id.loadinganimation);
        loadinganimation.setVisibility(View.GONE);
        spassword=view.findViewById(R.id.lpassword);
        seye=view.findViewById(R.id.leye);

        t=new ToastClass(getContext());

        i=0;
        s=new stepsindicator();

        seye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spassword.getText().toString().isEmpty())
                {
                    spassword.setError("Password Empty");
                    t.errortoast("Password Empty");
                }
                else
                {
                    if (i==0)
                    {
                        spassword.setTransformationMethod(null);
                        seye.setBackgroundResource(R.mipmap.eyeclosed);
                        i=1;
                    }
                    else
                    {
                        spassword.setTransformationMethod(new PasswordTransformationMethod());
                        seye.setBackgroundResource(R.mipmap.eyeopen);
                        i=0;
                    }
                }
            }
        });
        loginbtn=view.findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbtn.setClickable(false);
                loadinganimation.setVisibility(View.VISIBLE);

                if(semail.getText().toString().isEmpty())
                {
                    loadinganimation.setVisibility(View.INVISIBLE);
                    semail.setError("UID Empty");
                    t.errortoast("UID Empty");
                    loginbtn.setClickable(true);
                }
                else
                {
                    if(spassword.getText().toString().isEmpty())
                    {
                        spassword.setError("Password Empty");
                        loginbtn.setClickable(true);
                        t.errortoast("Password Empty");
                        loadinganimation.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        signinuser(semail.getText().toString()+"@cuchd.in",spassword.getText().toString());
                    }
                }
            }
        });


        return view;
    }

    private void signinuser(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                t.successtoaast("Logged in successfully");
                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user=FirebaseAuth.getInstance().getCurrentUser();
                        if(user!=null)
                        {

                            steps=snapshot.child("users").child(user.getUid()).child("Steps Completed").getValue(Integer.class);
                            if(steps<3)
                            {
                                openfrgment(steps);
                            }
                            else
                            {
                                gotohomepage();
                            }

                        }
                        else
                        {
                            loginbtn.setClickable(true);
                            loadinganimation.setVisibility(View.INVISIBLE);
                            t.errortoast("Some error happened");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        loadinganimation.setVisibility(View.INVISIBLE);
                        loginbtn.setClickable(true);
                        t.errortoast(error.getMessage());

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadinganimation.setVisibility(View.INVISIBLE);
                loginbtn.setClickable(true);
                t.warntoast(e.getMessage());

            }
        });
    }

    private void openfrgment(int steps) {
        loginbtn.setClickable(true);
        loadinganimation.setVisibility(View.INVISIBLE);
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();

        if(steps==1)
        {

            s.setI(2);
            fragmentManager.beginTransaction().setCustomAnimations(
                    // exit
                    R.anim.slide_up,   // popEnter
                    R.anim.slide_down  // popExit
            ).replace(R.id.logsincon,new Steptwo()).commit();

        }
        if(steps==2)
        {

            s.setI(3);
            fragmentManager.beginTransaction().setCustomAnimations(
                    // exit
                    R.anim.slide_up,   // popEnter
                    R.anim.slide_down  // popExit
            ).replace(R.id.logsincon,new stepthree()).commit();

        }
    }

    private void gotohomepage() {
        loadinganimation.setVisibility(View.INVISIBLE);
        loginbtn.setClickable(true);
        Intent intentmain = new Intent(getActivity(), MainActivity.class);
        startActivity(intentmain);

    }



}