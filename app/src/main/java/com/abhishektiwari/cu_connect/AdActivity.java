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
    String uid,steps;
    public Integer K=0;
    FirebaseUser user;
    Intent intentmain,intentlogin;
    public Integer i=0;
    SharedPreferences sharedpreferences;


    @Override
    protected void onStart() {
        super.onStart();
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
        sharedpreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        steps = sharedpreferences.getString("step", null);
        Toast.makeText(this, steps, Toast.LENGTH_SHORT).show();


        intentlogin = new Intent(AdActivity.this, LoginSignup.class);
        intentmain = new Intent(AdActivity.this, MainActivity.class);

        splash = findViewById(R.id.splash);
        splash.setVisibility(View.VISIBLE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (i == 0) {
              try {
                if (user != null) {

                    if (Integer.parseInt(steps) >= 3) {

                        K = 2;
                        setAds();


                    } else {
                        Toast.makeText(this, "in the 3rd loop", Toast.LENGTH_SHORT).show();

                        K = 1;
                        if(K!=null)
                        {
                            setAds();
                        }
                        else
                        {
                            Toast.makeText(this, "K is null", Toast.LENGTH_SHORT).show();
                        }

                    }

                  }

                else
                {

                    K=1;
                    setAds();
                }
                }
                catch(Exception e)
                {
                    K=1;
                    if(K!=null)
                    {
                        setAds();
                    }
                    else
                    {
                        Toast.makeText(this, "K is null", Toast.LENGTH_SHORT).show();
                    }

                }



                i++;
            }
        else
        {
            Toast.makeText(this, "in the loop", Toast.LENGTH_SHORT).show();

            K=1;
            setAds();

        }

        }


    public void setAds(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
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
            startActivity(intentmain);

        }
        else if(K==1)
        {
            startActivity(intentlogin);
        }
        else
        {
            startActivity(intentlogin);

        }
    }
}