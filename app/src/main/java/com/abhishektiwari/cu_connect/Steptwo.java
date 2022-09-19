package com.abhishektiwari.cu_connect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;


public class Steptwo extends Fragment {

    CardView next;
    stepsindicator s;
    EditText otp, phoneno;
    CardView views, verifyotpbtn;
    FirebaseUser user;
    boolean opened;
    ToastClass t;
    int i = 0;
    FirebaseAuth mAuth;
    SharedPreferences sharedpreferences;
    AlertDialog.Builder alert;
    LottieAnimationView lottieAnimationView;
    AlertDialog alertDialog;
    TextView otpstatus;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String Email;
    String Password;
    String uid;
    public int k = 0;
    String vid;
    View verified_email, backtosignup, resendemail;
    Boolean verified = false;
    SharedPreferences.Editor editor;

    public Steptwo() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (k == 0) {
            mAuth = FirebaseAuth.getInstance();
            sharedpreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
            Email = sharedpreferences.getString("Email", null);
            Password = sharedpreferences.getString("Password", null);
            uid = sharedpreferences.getString("Uid", null);
            editor = sharedpreferences.edit();

            loginnow(Email + "@cuchd.in", Password);

            try {
                if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                    alertDialog.dismiss();
                    verified = true;


                }
            } catch (Exception e) {
                t.errortoast(e.getMessage());
            }
            k++;
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_steptwo, container, false);
        t=new ToastClass(getContext());
        next=view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneno.setText("");
                otpstatus.setText("Send Otp");
                i=0;
                openmenu();
                otp.setText("");
            }
        });
        next.setVisibility(View.VISIBLE);
        views = view.findViewById(R.id.otpbox);
        lottieAnimationView=view.findViewById(R.id.loadinganimation);
        lottieAnimationView.setVisibility(View.INVISIBLE);
        views.setVisibility(View.INVISIBLE);
        s = new stepsindicator();
        s.setI(2);
        alert = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.emailnotverified_dialog, null);
        alert.setView(mView);
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
        verified_email = alertDialog.findViewById(R.id.recheck);
        backtosignup = alertDialog.findViewById(R.id.backtosignup);
        resendemail = alertDialog.findViewById(R.id.resendmail);
        resendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                if (user != null) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            t.successtoaast("verification Email again Sent on :" + Email + "cuchd.in");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            t.errortoast(e.getMessage());

                        }
                    });
                }
            }
        });
        backtosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    s.setI(1);
                    alertDialog.dismiss();
                    fragmentManager.beginTransaction().setCustomAnimations(
                            // exit
                            R.anim.slide_up,   // popEnter
                            R.anim.slide_down  // popExit
                    ).replace(R.id.logsincon, new Sign_up()).commit();
                } catch (Exception e) {

                }


            }
        });
        verified_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginnow(Email + "@cuchd.in", Password);
            }
        });

        otp = view.findViewById(R.id.otp);
        phoneno = view.findViewById(R.id.phoneno);


        if (Email != null && Password != null) {
            loginnow(Email + "@cuchd.in", Password);
        }
        otpstatus = view.findViewById(R.id.otpstatustext);
        otpstatus.setText("Send Otp");

        i = 0;
        verifyotpbtn = view.findViewById(R.id.verifynumber);
        verifyotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setVisibility(View.INVISIBLE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                if (i == 0) {
                    if (phoneno.getText().toString().isEmpty()) {
                        phoneno.setError("Enter Phone No.");
                    } else if (phoneno.getText().toString().trim().length() != 10) {
                        phoneno.setError("Invalid Phone No.");
                    } else {
                        otpstatus.setText("Sending OTP..");
                        verifyPhoneno();
                    }
                } else {
                    if (!otp.getText().toString().isEmpty()) {
                        t.warntoast("Verifying phone no..");
                        verifyCode(otp.getText().toString());

                    }
                    else
                    {
                        next.setVisibility(View.VISIBLE);
                        lottieAnimationView.setVisibility(View.INVISIBLE);

                        t.errortoast("Please Enter OTP");

                    }
                }


            }
        });
        return view;
    }

    private void phonenoverified() {
        next.setVisibility(View.VISIBLE);
        lottieAnimationView.setVisibility(View.INVISIBLE);
        t.successtoaast("User Verified");
        otpstatus.setText("Verified");
        editor.putString("step", "2");
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Phone no").setValue(phoneno.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Whatsapp no").setValue("null");
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Phone Verified").setValue("Yes");
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Steps Completed").setValue(2);

        try {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            s.setI(3);
            fragmentManager.beginTransaction().setCustomAnimations(
                    // exit
                    R.anim.slide_up,   // popEnter
                    R.anim.slide_down  // popExit
            ).replace(R.id.logsincon, new stepthree()).commit();
        }catch (Exception e)
        {

        }


    }

    private void loginnow(String s, String password) {

        mAuth.signInWithEmailAndPassword(s, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {


                try {
                    if (mAuth.getCurrentUser().isEmailVerified()) {
                        t.successtoaast("User is e-mail verified");
                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("E-mail Verified").setValue("Yes");
                        alertDialog.dismiss();

                    } else {
                        t.warntoast("e-mail not verified till now");

                    }
                } catch (Exception e) {
                    alertDialog.show();
                    t.errortoast(e.getMessage());
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alertDialog.show();
                t.errortoast(e.getMessage());
            }
        })
        ;
    }

    private void verifyPhoneno() {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                lottieAnimationView.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Phone no").setValue(phoneno.toString());
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                lottieAnimationView.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
                t.errortoast(e.getMessage());

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                lottieAnimationView.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
                otpstatus.setText("Verify Otp");
                vid = verificationId;
                i = 1;
                openmenu();


            }

        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber("+91" + phoneno.getText().toString())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void openmenu() {
        otp.requestFocus();
        if (!opened) {
            views.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(
                    -views.getWidth(),
                    0,
                    0,
                    0);
            animate.setDuration(1000);
            animate.setFillAfter(true);
            views.startAnimation(animate);
        } else {
            views.setVisibility(View.INVISIBLE);
            TranslateAnimation animate = new TranslateAnimation(
                    0,
                    -views.getWidth(),
                    0,
                    0);
            animate.setDuration(1000);
            animate.setFillAfter(true);
            views.startAnimation(animate);
        }
        opened = !opened;
    }

    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vid, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);

    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth.getInstance().getCurrentUser().delete();
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(Email + "@cuchd.in", Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    phonenoverified();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    t.errortoast(e.getMessage());
                                    lottieAnimationView.setVisibility(View.INVISIBLE);
                                    next.setVisibility(View.VISIBLE);
                                }
                            });

                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            t.errortoast(task.getException().getMessage());
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                            next.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}