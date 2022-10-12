package com.abhishektiwari.cu_connect;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Setting extends Fragment {
    Float version;
    ImageView profile;
    TextView uidtext,infotext,version_text;
    //
    TextView logoutbtn,update_pno,addwhatsappno,sendpasswordresetmail;
    EditText phoneno,whatsappnot;
    //
    CardView version_card,whatsappno,update_password,update_phone_no,delete_account,logout_account,invite_friend;
    CardView loading;
    String branch,semester,uid;
    public Setting() {
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        uidtext=view.findViewById(R.id.uid);
        version_text=view.findViewById(R.id.version_text);
        version_card=view.findViewById(R.id.version_card);
        infotext=view.findViewById(R.id.info);
        profile=view.findViewById(R.id.item_profile_picture);
        loading=view.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);


        //
        whatsappno=view.findViewById(R.id.add_whatsapp_no);
        update_password=view.findViewById(R.id.update_password);
        update_phone_no=view.findViewById(R.id.update_phone_no);
        delete_account=view.findViewById(R.id.delete_account);
        logout_account=view.findViewById(R.id.logout_account);
        invite_friend=view.findViewById(R.id.invite_friend);
        //
        //
        whatsappno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getContext());
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                dialog.setContentView(R.layout.add_wa_no);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
                whatsappnot=dialog.findViewById(R.id.whatsappno);
                addwhatsappno=dialog.findViewById(R.id.addwhatsappno);
                addwhatsappno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(whatsappnot.getText().length()==10)
                        {
                            FirebaseDatabase.getInstance().getReference().child("Profile info").child(uid).child("Whatsapp no").setValue(whatsappnot.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Whatsapp no").setValue(whatsappnot.getText().toString());
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Whatsapp no updated", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            whatsappnot.setText("");
                            Toast.makeText(getContext(), "Wrong Format", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getContext());
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                dialog.setContentView(R.layout.update_password);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
                sendpasswordresetmail=dialog.findViewById(R.id.sendpasswordresetmail);
                sendpasswordresetmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(uid+"@cuchd.in").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Reset Mail sent successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
                dialog.show();

            }
        });
        update_phone_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getContext());
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                dialog.setContentView(R.layout.add_phone_no);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
                phoneno=dialog.findViewById(R.id.phoneno);
                update_pno=dialog.findViewById(R.id.update_pno);
                update_pno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(phoneno.getText().length()==10)
                        {
                            FirebaseDatabase.getInstance().getReference().child("Profile info").child(uid).child("Phone No").setValue(phoneno.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Phone No").setValue(phoneno.getText().toString());
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Phone no updated", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            whatsappnot.setText("");
                            Toast.makeText(getContext(), "Wrong Format", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getContext());
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                dialog.setContentView(R.layout.delete_account);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        logout_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getContext());
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                dialog.setContentView(R.layout.logout_accounts);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
                logoutbtn=dialog.findViewById(R.id.logoutbtn);
                logoutbtn.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                FirebaseAuth.getInstance().signOut();
                                Intent intent=new Intent(getActivity(),LoginSignup.class);
                                startActivity(intent);
                            }
                        }
                );
                dialog.show();
            }
        });
        invite_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Inviting you");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Here is a very good app for the Students :: \n"+"https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID);
                startActivity(Intent.createChooser(sharingIntent, "Sharing"));

            }
        });
        //
        version_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID));
                startActivity(viewIntent);
            }
        });
        getProfilevalues();
        return  view;
    }



    private void getProfilevalues() {
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                version=snapshot.child("Version").getValue(Float.class);
                branch=snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("Branch").getValue(String.class);
                semester=snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("Semester").getValue(String.class);
                uid=snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("College UID").getValue(String.class);
                int x=snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("Element").getValue(Integer.class);
                showelement(x);
                SetProfileText(branch,semester,uid,version);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void SetProfileText(String branch, String semester, String uid, Float version) {
        infotext.setText(branch+" , "+semester);
        uidtext.setText(uid+"@cuchd.in");
        Float previous_version=Float.parseFloat(BuildConfig.VERSION_NAME);
        if(previous_version.equals(version))
        {

            version_text.setText("Latest Version !!");
            version_card.setCardBackgroundColor(getResources().getColor(R.color.green));
        }
        else
        {
            version_text.setText("New Version Available !!");
            version_card.setCardBackgroundColor(getResources().getColor(R.color.yellow));

        }

    }
    private void showelement(int x) {
        loading.setVisibility(View.GONE);
        if(x==0)
        {
            profile.setImageResource(R.mipmap.fire);
        }
        else if(x==1)
        {
            profile.setImageResource(R.mipmap.water);
        }
        else if(x==2)
        {
            profile.setImageResource(R.mipmap.air);
        }
        else if(x==3)
        {
            profile.setImageResource(R.mipmap.earth);
        }
        else if(x==4)
        {
            profile.setImageResource(R.mipmap.spirit);
        }
        else
        {
            profile.setImageResource(R.mipmap.profile);
        }
    }

}