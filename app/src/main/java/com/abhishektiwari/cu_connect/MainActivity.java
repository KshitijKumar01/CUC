package com.abhishektiwari.cu_connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    //bottom nevigation
    CardView profilebg,companionbg,homebg,searchbg,settingbg;
    FloatingActionButton floatingActionButton;

    //drawer layout
    DrawerLayout drawerLayout;
    MaterialToolbar toolbar;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Hello world");

        //Bottomnevigation
        profilebg=findViewById(R.id.profileitem);
        searchbg=findViewById(R.id.searchitem);
        settingbg=findViewById(R.id.settingitem);
        homebg=findViewById(R.id.homeitem);
        companionbg=findViewById(R.id.companionitem);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.home);
        HomeClicked();
        getSupportFragmentManager().beginTransaction().add(R.id.maincontainer,new Home()).commit();

        //drawerlayout
        toolbar=findViewById(R.id.drawer_toolbar);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {
                    case R.id.about_app:

                        break;
                    case R.id.connect_to_us:

                        break;

                    case R.id.our_more_apps:

                        break;
                    case R.id.privacy_policy:

                        break;

                    default:
                        return true;


                }
                return true;
            }
        });

    }



    private void ChangenavbarBackground(int a) {
        if(a==1)
        {
            HomenotClicked();
            profilebg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.silver)));
            settingbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
            searchbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
            companionbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));

        }
        else if(a==2)
        {
            HomenotClicked();
            profilebg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
            settingbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
            searchbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
            companionbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.silver)));
        }
        else if(a==3)
        {
            profilebg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
            settingbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
            searchbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
            companionbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));

            HomeClicked();
        }
        else if(a==4)
        {
            HomenotClicked();
            profilebg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
            settingbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
            searchbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.silver)));
            companionbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));

        }
        else if(a==5)
        {
            HomenotClicked();
            profilebg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
            settingbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.silver)));
            searchbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
            companionbg.setBackgroundTintList(  ColorStateList.valueOf(getResources().getColor(R.color.transparent)));

        }


    }

    private void HomenotClicked() {
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.redmain)));
        ImageViewCompat.setImageTintList(
                floatingActionButton,
                ColorStateList.valueOf(getResources().getColor(R.color.white))
        );
    }

    private void HomeClicked() {

        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.redmain)));
        ImageViewCompat.setImageTintList(
                floatingActionButton,
                ColorStateList.valueOf(getResources().getColor(R.color.silver))
        );
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile:

                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new Profile()).commit();
                ChangenavbarBackground(1);
                break;
            case R.id.companion:

                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new companion_main()).commit();
                ChangenavbarBackground(2);

                break;
            case R.id.home:

                ChangenavbarBackground(3);
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new Home()).commit();

                break;
            case R.id.search:

                ChangenavbarBackground(4);
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new Search()).commit();

                break;
            case R.id.setting:

                ChangenavbarBackground(5);
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new Setting()).commit();

                break;
        }
    }
}