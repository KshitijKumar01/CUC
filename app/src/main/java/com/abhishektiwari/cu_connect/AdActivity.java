package com.abhishektiwari.cu_connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    ConstraintLayout splash;
    public Integer K=0;
    public  static  Integer steps;
    FirebaseUser user;
    Intent intentmain,intentlogin;
    private  Integer i=0;
    ToastClass t=new ToastClass(this);





    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });





        intentlogin = new Intent(AdActivity.this, LoginSignup.class);
        intentmain = new Intent(AdActivity.this, MainActivity.class);

        splash = findViewById(R.id.splash);
        splash.setVisibility(View.VISIBLE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (i==0) {
            checkstep();
            i++;
        }
        else
        {
            K=1;
            setAds();
        }
    }

    private void checkstep() {
        try {
            if (user != null) {
                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        steps=snapshot.child("users").child(user.getUid()).child("Steps Completed").getValue(Integer.class);
                        if(steps!=null)
                        {
                            try
                            {
                                if (steps >= 3) {

                                    K = 2;
                                    setAds();


                                } else {

                                    K = 1;
                                    setAds();


                                }

                            }
                            catch (Exception e)
                            {
                                t.errortoast(e.getMessage());

                                K = 1;
                                setAds();
                            }
                        }
                        else
                        {
                            K = 1;
                            setAds();
                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        t.errortoast(error.getMessage());
                        K = 1;
                        setAds();
                    }
                });
            }
            else
            {
               t.warntoast("No user found");
                K=1;
                setAds();
            }
        }
        catch(Exception e)
        {
            t.errortoast(e.getMessage());
            K=1;
            setAds();




        }

    }


    public void setAds(){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-3817807149121122/6550132029", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        splash.setVisibility(View.GONE);
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                changeActivity();
                                // Called when a click is recorded for an ad.
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                mInterstitialAd = null;
                                changeActivity();

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                mInterstitialAd = null;
                                changeActivity();
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                            }
                        });

                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(AdActivity.this);
                            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when ad is dismissed.
                                    // Set the ad reference to null so you don't show the ad a second time.
                                  changeActivity();
                                }
                            });
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });
    }
    public void changeActivity()

    {
        mInterstitialAd = null;
        if(K==2)
        {
            intentmain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentmain);
            this.finishActivity(0);
        }
        else if(K==1)
        {
            intentlogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentlogin);
            this.finish();
        }
        else
        {
            intentlogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity(intentlogin);
            this.finish();
        }
    }
}