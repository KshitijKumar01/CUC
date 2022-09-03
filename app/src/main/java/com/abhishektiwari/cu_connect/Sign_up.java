package com.abhishektiwari.cu_connect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_up extends Fragment {
    CardView next;
    ImageView seye,seyeagain;
    int i,j;
    EditText semail,spassword,spasswordagain;
    String Email,Password;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    SharedPreferences sharedpreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
       sharedpreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        next=view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            semail.setError("Enter UID");
        }
        else if(spassword.getText().toString().isEmpty())
        {
            spassword.setError("Enter Password");
        }
        else if(spasswordagain.getText().toString().isEmpty())
        {
            spasswordagain.setError("Enter Password Again");
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
                spasswordagain.setError("Password Mismatch");

            }
        }

    }

    private void createUser(String email,String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

            mAuth.createUserWithEmailAndPassword(email+"@cuchd.in",password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            SendVerificationmail();
                            Toast.makeText(getContext(), "sending email", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Reason : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    private void SendVerificationmail() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "EmailSend", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Email", Email);
                editor.putString("Password", Password);
                editor.putString("1", Password);
                editor.apply();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.logsincon,new Steptwo()).commit();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}