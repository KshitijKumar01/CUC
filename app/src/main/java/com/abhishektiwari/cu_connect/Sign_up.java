package com.abhishektiwari.cu_connect;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_up extends Fragment {
    CardView next;
    ImageView seye,seyeagain;
    int i,j;
    EditText semail,spassword,spasswordagain;
    String Email,Password,uid;
    FirebaseAuth mAuth ;
    FirebaseUser user ;
    SharedPreferences sharedpreferences;
    LottieAnimationView lottieAnimationView;
    ToastClass t;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        sharedpreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        t=new ToastClass(getContext());
        next=view.findViewById(R.id.next);
        lottieAnimationView=view.findViewById(R.id.loadinganimation);
        lottieAnimationView.setVisibility(View.INVISIBLE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next.setVisibility(View.INVISIBLE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                CheckText();
            }
        });

        semail=view.findViewById(R.id.semail);
        spassword=view.findViewById(R.id.spassword);
        spasswordagain=view.findViewById(R.id.spasswordagain);
        seye=view.findViewById(R.id.eye);
        seyeagain=view.findViewById(R.id.eyeagain);
        i=0;

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

        j=0;
        seyeagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spassword.getText().toString().isEmpty())
                {
                    spassword.setError("Password Empty");
                    t.errortoast("Password Empty");
                }
                else
                {
                    if (j==0)
                    {
                        spasswordagain.setTransformationMethod(null);
                        seyeagain.setBackgroundResource(R.mipmap.eyeclosed);
                        j=1;
                    }
                    else
                    {
                        spasswordagain.setTransformationMethod(new PasswordTransformationMethod());
                        seyeagain.setBackgroundResource(R.mipmap.eyeopen);
                        j=0;
                    }
                }
            }
        });






        return view;
    }


    private void CheckText() {
        if(semail.getText().toString().isEmpty())
        {
            lottieAnimationView.setVisibility(View.INVISIBLE);
            semail.setError("Enter UID");
            t.errortoast("Enter UID");
            next.setVisibility(View.VISIBLE);
        }
        else if(spassword.getText().toString().isEmpty())
        {
            lottieAnimationView.setVisibility(View.INVISIBLE);
            spassword.setError("Enter Password");
            t.errortoast("Password Empty");
            next.setVisibility(View.VISIBLE);
        }
        else if(spasswordagain.getText().toString().isEmpty())
        {
            lottieAnimationView.setVisibility(View.INVISIBLE);
            spasswordagain.setError("Enter Password Again");
            t.errortoast("Enter Password Again");
            next.setVisibility(View.VISIBLE);

        }
        else
        {
            if(spassword.getText().toString().equals(spasswordagain.getText().toString()))
            {

                Email=semail.getText().toString();
                Password=spasswordagain.getText().toString();
                createUser(Email,Password);

            }
            else
            {
                next.setVisibility(View.VISIBLE);
                lottieAnimationView.setVisibility(View.INVISIBLE);
                spasswordagain.setError("Password Mismatch");
                t.errortoast("Password Mismatch");


            }
        }

    }

    private void createUser(String email,String password) {
        mAuth = FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();

            mAuth.createUserWithEmailAndPassword(email+"@cuchd.in",password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {

                                    Signinuser(email,password,0);

                            }
                            else if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Signinuser(email,password,1);
                                next.setVisibility(View.VISIBLE);
                                lottieAnimationView.setVisibility(View.INVISIBLE);
                                t.warntoast("This Email is already registered go to Login Page");
                            }
                            else  {
                                t.errortoast(task.getException().getMessage());
                                next.setVisibility(View.VISIBLE);
                                lottieAnimationView.setVisibility(View.INVISIBLE);
                            }

                        }
                    });
        }

    private void Signinuser(String email, String password,int condition)
    {
        if(condition==0)
        {
            mAuth.signInWithEmailAndPassword(email+"@cuchd.in",password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    SendVerificationmail();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    t.errortoast(e.getMessage());
                    next.setVisibility(View.VISIBLE);
                    lottieAnimationView.setVisibility(View.INVISIBLE);

                }
            });

        }
        else if(condition==1)
        {
            showdialog();
            next.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.INVISIBLE);

        }
        else
        {

        }

    }

    private void showdialog() {
        Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.gotologin);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void SendVerificationmail() {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    uid=FirebaseAuth.getInstance().getUid();
                    t.successtoaast("Email Sent on : "+Email+"cuchd.in");
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("Email",Email);
                    editor.putString("Uid",uid);
                    editor.putString("Password", Password);
                    editor.putString("step", String.valueOf(1));
                    editor.apply();

                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("College UID").setValue(Email);
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Password").setValue(Password);
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Phone no").setValue("null");
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("E-mail Verified").setValue("null");
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Whatsapp no").setValue("null");
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Steps Completed").setValue(1);
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Phone Verified").setValue("null");
                    try {
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.beginTransaction().setCustomAnimations(
                                // exit
                                R.anim.slide_up,   // popEnter
                                R.anim.slide_down  // popExit
                        ).replace(R.id.logsincon,new Steptwo()).commit();

                    }
                    catch (Exception e)
                    {

                    }



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    t.errortoast(e.getMessage());
                    next.setVisibility(View.VISIBLE);
                    lottieAnimationView.setVisibility(View.INVISIBLE);


                }
            });
        }

    }


}