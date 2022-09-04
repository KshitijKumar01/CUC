package com.abhishektiwari.cu_connect;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;


public class Steptwo extends Fragment {

    stepsindicator s;
    EditText otp,phoneno;
    CardView views,verifyotpbtn;
    boolean opened;
     Handler handler;
    int i=0;
    SharedPreferences sharedpreferences;
    AlertDialog.Builder alert;
     AlertDialog alertDialog;
    TextView otpstatus;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String Email,Password,uid,vid;
    public Steptwo() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_steptwo, container, false);

        views = view.findViewById(R.id.otpbox);
        views.setVisibility(View.INVISIBLE);
        sharedpreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        Email=sharedpreferences.getString("Email",null);
        Password=sharedpreferences.getString("Password",null);
        uid=sharedpreferences.getString("Uid",null);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("step", String.valueOf(2));
        s=new stepsindicator();
        s.setI(2);
        alert = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.emailnotverified_dialog,null);
        alert.setView(mView);
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        otp=view.findViewById(R.id.otp);
        phoneno=view.findViewById(R.id.phoneno);

        handler = new Handler();

        if(Email!=null && Password!=null)
        {
            Toast.makeText(getContext(), "Email"+Email+Password, Toast.LENGTH_SHORT).show();
            loginnow(Email+"@cuchd.in",Password);
        }
        otpstatus=view.findViewById(R.id.otpstatustext);

        otpstatus.setText("Send Otp");

        i=0;
        verifyotpbtn=view.findViewById(R.id.verifynumber);
        verifyotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0)
                {
                    if(phoneno.getText().toString().isEmpty())
                    {
                        phoneno.setError("Enter Phone No.");
                    }
                    else if(phoneno.getText().toString().trim().length()!=10)
                    {
                        phoneno.setError("Invalid Phone No.");
                    }
                    else
                    {
                        otpstatus.setText("Sending OTP..");
                        verifyPhoneno();
                    }
                }
                else
                {
                    if(!otp.getText().toString().isEmpty())
                    {
                        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(vid,otp.getText().toString());
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getContext(), "User verified", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Phone no").setValue(phoneno.getText().toString());
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Whatsapp no").setValue("null");
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Phone Verified").setValue("Yes");
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("E-mail Verified").setValue("Yes");
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Phone auth Id").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                                s.setI(3);
                                fragmentManager.beginTransaction().setCustomAnimations(
                                        // exit
                                        R.anim.slide_up,   // popEnter
                                        R.anim.slide_down  // popExit
                                ).replace(R.id.logsincon,new stepthree()).commit();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }


            }
        });






        return  view;
    }

    private void loginnow(String s, String password) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(s,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified())
                        {
                            alertDialog.dismiss();
                        }
                        else
                        {
                            alertDialog.show();
                        }
                        handler.postDelayed(this, 1000);
                    }

                });



                if(firebaseAuth.getCurrentUser().isEmailVerified())
                {

                    Toast.makeText(getContext(), "Email verified", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();

                }
                else
                {
                    btn_showMessage();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error , Reason : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        ;
    }

    private void verifyPhoneno() {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {


                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Phone no").setValue(phoneno.toString());
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                otpstatus.setText("Verify Otp");
                vid=verificationId;
                i=1;
                openmenu();

            }

        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber("+91"+phoneno.getText().toString())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void openmenu() {
        otp.requestFocus();
        if(!opened){
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

    public void btn_showMessage(){



    }}