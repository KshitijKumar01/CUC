package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;


public class companion_main extends Fragment {


    TextView companion_button,roommate_button;
    CardView compbg,roombg;
    Boolean companion,room;
    // TODO: Rename and change types of parameters


    public companion_main() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_companion_main, container, false);
        companion_button=view.findViewById(R.id.companion_button);
        roommate_button=view.findViewById(R.id.roommates_button);
        compbg=view.findViewById(R.id.compbg);
        roombg=view.findViewById(R.id.roombg);
        roombg.setVisibility(View.INVISIBLE);
        compbg.setVisibility(View.VISIBLE);
        companion=true;
        room=false;

        FragmentManager fragmentmanager=getActivity().getSupportFragmentManager();
        fragmentmanager.beginTransaction().replace(R.id.companion_main,new Companion()).commit();

        companion_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!companion)
                {
                    roommate_button.setTextColor(getResources().getColor( R.color.black));
                    companion_button.setTextColor(getResources().getColor( R.color.white));

                    compbg.setVisibility(View.VISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(
                            compbg.getWidth(),
                            0,
                            0,
                            0);
                    animate.setDuration(1000);
                    animate.setFillAfter(true);
                    compbg.startAnimation(animate);
                    fragmentmanager.beginTransaction().setCustomAnimations(
                            // exit
                            R.anim.slide_in_left,   // popEnter
                            R.anim.slide_out_right  // popExit
                    ).replace(R.id.companion_main,new Companion()).commit();
                    roombg.setVisibility(View.VISIBLE);
                    TranslateAnimation animate2 = new TranslateAnimation(
                            0,
                            roombg.getWidth(),
                            0,
                            0);
                    animate2.setDuration(1000);
                    animate2.setFillAfter(true);
                    roombg.startAnimation(animate2);

                }
                companion=true;
                room=false;
            }
        });
        roommate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!room)
                {
                    fragmentmanager.beginTransaction().setCustomAnimations(
                            // exit
                            R.anim.slide_in_right,   // popEnter
                            R.anim.slide_out_left  // popExit
                    ).replace(R.id.companion_main,new roommate()).commit();
                    roommate_button.setTextColor(getResources().getColor( R.color.white));
                    companion_button.setTextColor(getResources().getColor( R.color.black));
                    compbg.setVisibility(View.VISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            compbg.getWidth(),
                            0,
                            0);
                    animate.setDuration(1000);
                    animate.setFillAfter(true);
                    compbg.startAnimation(animate);

                    roombg.setVisibility(View.VISIBLE);
                    TranslateAnimation animate2 = new TranslateAnimation(
                            roombg.getWidth(),
                            0,
                            0,
                            0);
                    animate2.setDuration(1000);
                    animate2.setFillAfter(true);
                    roombg.startAnimation(animate2);

                }
                room=true;
                companion=false;

            }
        });



        return  view;
    }

}