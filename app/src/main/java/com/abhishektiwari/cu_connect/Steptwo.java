package com.abhishektiwari.cu_connect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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

import java.util.concurrent.TimeUnit;


public class Steptwo extends Fragment {

    EditText otp1,otp2,otp3,otp4;
    CardView views,verifyotpbtn;
    boolean opened;
    Number Phoneno;
    SharedPreferences sharedpreferences;

    String Email,Password;
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
        otp1=view.findViewById(R.id.otp1);
        otp2=view.findViewById(R.id.otp2);
        otp3=view.findViewById(R.id.otp3);
        otp4=view.findViewById(R.id.otp4);
        views = view.findViewById(R.id.otpbox);
        views.setVisibility(View.INVISIBLE);
        verifyotpbtn=view.findViewById(R.id.verifynumber);
        verifyotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmenu();
            }
        });
        sharedpreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);

        Email=sharedpreferences.getString("Email",null);
        Password=sharedpreferences.getString("Password",null);

        if(Email!=null && Password!=null)
        {
            Toast.makeText(getContext(), "Email"+Email+Password, Toast.LENGTH_SHORT).show();
            loginnow(Email+"@cuchd.in",Password);
        }

        EditText[] edit = {(EditText) otp1, (EditText) otp2,(EditText) otp3, (EditText) otp4};
        otp1.addTextChangedListener(new textwatcher(otp1, edit));
        otp2.addTextChangedListener(new textwatcher(otp2, edit));
        otp3.addTextChangedListener(new textwatcher(otp3, edit));
        otp4.addTextChangedListener(new textwatcher(otp4, edit));



        return  view;
    }

    private void loginnow(String s, String password) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(s,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                if(firebaseAuth.getCurrentUser().isEmailVerified())
                {
                    
                    Toast.makeText(getContext(), "Email verified", Toast.LENGTH_SHORT).show();
                    verifyPhoneno(Phoneno);
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

    private void verifyPhoneno(Number phoneno) {
       /* PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(phoneno.toString())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.


                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                // Save verification ID and resending token so we can use them later

            }
        };*/
    }

    private void openmenu() {
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
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.emailnotverified_dialog,null);

        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();


    }}