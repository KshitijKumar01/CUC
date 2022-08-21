package com.abhishektiwari.cu_connect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class LoginSignup extends AppCompatActivity implements CallbackFragment{

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        addFragment();
    }

    public void addFragment(){
        LoginFragment fragment = new LoginFragment();
        fragment.setCallbackFragment(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment(){
        fragment = new SignInFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void changeFragment() {
        replaceFragment();
    }
}