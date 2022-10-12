package com.abhishektiwari.cu_connect;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class home_posts_recycler extends RecyclerView.Adapter<home_posts_recycler.Holder> {
    Context context;
    ArrayList<home_post_data> home_post_d;
    Calendar c;
    String date="";
    long followers_count,followings_count;
    int shares;

    public home_posts_recycler(Context context,ArrayList<home_post_data> home_post_d) {
        this.context=context;
        this.home_post_d=home_post_d;
    }

    @NonNull
    @Override
    public home_posts_recycler.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.posts,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull home_posts_recycler.Holder holder, @SuppressLint("RecyclerView") int position) {

        if(!home_post_d.get(position).getImageurl().equals("null"))
        {
            Picasso.get()
                    .load(String.valueOf(home_post_d.get(position).getImageurl()))
                    .into(holder.postimg);
        }
        else {
            holder.postimg.setVisibility(View.GONE);
        }
        holder.posttext.setText(home_post_d.get(position).getPost_text());
        holder.postdate.setText(home_post_d.get(position).getDate());
        holder.postby.setText("by "+home_post_d.get(position).getUid());
        holder.postlikestext.setText(String.valueOf(home_post_d.get(position).getLike()));
        try
        {
            int x=home_post_d.get(position).getElement();
            if(x==0)
            {
                holder.profileimg.setImageResource(R.mipmap.fire);
            }
            else if(x==1)
            {
                holder.profileimg.setImageResource(R.mipmap.water);
            }
            else if(x==2)
            {
                holder.profileimg.setImageResource(R.mipmap.air);
            }
            else if(x==3)
            {
                holder.profileimg.setImageResource(R.mipmap.earth);
            }
            else if(x==4)
            {
                holder.profileimg.setImageResource(R.mipmap.spirit);
            }
            else
            {
                holder.profileimg.setImageResource(R.mipmap.logo);
            }

        }
        catch (Exception e)
        {

        }
        holder.save_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy  'at' HHmmss");
                date = sdf.format(new Date());
                try
                {
                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Saved Posts").child(date).setValue(home_post_d.get(position));
                    Toast.makeText(context, "Post Saved Successfully..", Toast.LENGTH_SHORT).show();

                }
                catch (Exception e)

                {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.share_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try
                        {
                            shares=snapshot.child("Profile info").child(home_post_d.get(position).getUid()).child("Likes").getValue(Integer.class);
                            int k=shares+1;
                            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    String myuid=snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("College UID").getValue(String.class);
                                    if(!myuid.equals(home_post_d.get(position).getUid()))
                                    {
                                        FirebaseDatabase.getInstance().getReference().child("Profile info").child(home_post_d.get(position).getUid()).child("Likes").setValue(k);

                                    }



                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }
                        catch (Exception e)
                        {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                holder.postimg.buildDrawingCache();
                Bitmap image = holder.postimg.getDrawingCache();
                Intent share = new Intent(Intent.ACTION_SEND);

                if(image!=null)
                {
                    share.setType("image/*");
                    share.putExtra(Intent.EXTRA_STREAM, getImageUri(context,image));
                }
                else
                {
                    share.setType("text/plain");

                }

                String s=(home_post_d.get(position).getPost_link().equals("null"))?" ":"Link : "+home_post_d.get(position).getPost_link()+"\n";
                String shareMessage= home_post_d.get(position).getPost_text()+"\n\n"+s+"\nConnect to cu on::\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                share.putExtra(Intent.EXTRA_TEXT, shareMessage);
                context.startActivity(Intent.createChooser(share,"Share via"));
            }
        });
        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  'at' HH:mm:ss");
                date = sdf.format(new Date());

                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        String myuid=snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("College UID").getValue(String.class);
                        if(!myuid.equals(home_post_d.get(position).getUid()))
                        {
                            followdata f=new followdata(home_post_d.get(position).getUid(),date);
                            followdata f2=new followdata(myuid,date);
                            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Following_list").child(home_post_d.get(position).getUid()).setValue(f);
                            FirebaseDatabase.getInstance().getReference().child("Profile info").child(home_post_d.get(position).getUid()).child("Followers_list").child(myuid).setValue(f2);

                            FirebaseDatabase.getInstance().getReference().child("Profile info").child(home_post_d.get(position).getUid()).child("Followers_list").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    followers_count=snapshot.getChildrenCount();
                                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Following_list").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            followings_count=snapshot.getChildrenCount();
                                            FirebaseDatabase.getInstance().getReference().child("Profile info").child(myuid).child("Following").setValue(Integer.valueOf((int) followings_count));
                                            FirebaseDatabase.getInstance().getReference().child("Profile info").child(home_post_d.get(position).getUid()).child("Followers").setValue(Integer.valueOf((int) followers_count));

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }
                        else
                        {
                            Toast.makeText(context, "You can't follow yourself", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    @Override
    public int getItemCount() {
        return home_post_d.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView postimg,profileimg;
        ImageButton save_post,follow,share_post;
        TextView posttext,postlikestext,postdate,postby;
        public Holder(@NonNull View itemView) {
            super(itemView);
            postlikestext=itemView.findViewById(R.id.postliketext);
            postimg=itemView.findViewById(R.id.postimg);
            posttext=itemView.findViewById(R.id.posttext);
            postdate=itemView.findViewById(R.id.date);
            postby=itemView.findViewById(R.id.postby);
            follow=itemView.findViewById(R.id.postfollow);
            save_post=itemView.findViewById(R.id.post_save);
            share_post=itemView.findViewById(R.id.postshare);
            profileimg=itemView.findViewById(R.id.profileimg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new webview()).commit();
                }
            });

        }
    }
}
