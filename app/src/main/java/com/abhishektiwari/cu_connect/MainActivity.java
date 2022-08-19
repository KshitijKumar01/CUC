package com.abhishektiwari.cu_connect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.FragmentManager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    CardView profilebg,companionbg,homebg,searchbg,settingbg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Hello world");

        profilebg=findViewById(R.id.profileitem);
        searchbg=findViewById(R.id.searchitem);
        settingbg=findViewById(R.id.settingitem);
        homebg=findViewById(R.id.homeitem);
        companionbg=findViewById(R.id.companionitem);

        profilebg.setBackgroundColor(ContextCompat.getColor(this, R.color.black));




        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.home);
        ImageViewCompat.setImageTintList(
                floatingActionButton,
                ColorStateList.valueOf(Color.WHITE)
        );



        getSupportFragmentManager().beginTransaction().add(R.id.maincontainer,new Home()).commit();

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile:

                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new Profile()).commit();
                break;
            case R.id.companion:

                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new Companion()).commit();

                break;
            case R.id.home:

                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new Home()).commit();

                break;
            case R.id.search:

                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new Search()).commit();

                break;
            case R.id.setting:

                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new Setting()).commit();

                break;
        }
    }
}