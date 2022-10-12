package com.abhishektiwari.cu_connect;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class roommate extends Fragment {


    RecyclerView recyclerView;
    Dialog dialog;
    String froms,tos;
    roommate_adapter roommate_adapter;
    List<String> ina,persona;
    ImageView addrequest;
    String uid,uidt;
    Calendar c;
    int count;
    CardView loading;
    ArrayList<Roommate_request_post_data> arrroommates;

    public roommate() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_roommate, container, false);
        recyclerView=view.findViewById(R.id.roommates_recycler);
        arrroommates=new ArrayList<>();
        roommate_adapter =new roommate_adapter(getContext(),arrroommates);
        recyclerView.setAdapter(roommate_adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loading=view.findViewById(R.id.loading);
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.child("Roommates Requests").getChildren()) {

                    try {
                        Roommate_request_post_data cr=dataSnapshot.getValue(Roommate_request_post_data.class);
                        arrroommates.add(cr);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                roommate_adapter.notifyDataSetChanged();
                loading.setVisibility(View.GONE);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        addrequest=view.findViewById(R.id.addroomrequest);
        addrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addpostdialog();
            }
        });


        return view;
    }
    void addpostdialog() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        SharedPreferences sharedpreferences;
        sharedpreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        uid = sharedpreferences.getString("Email", null);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.roommates_posts);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout((6 * width)/7, (4 * height)/7);
        dialog.show();
        EditText message=dialog.findViewById(R.id.type_text);
        TextView  from=dialog.findViewById(R.id.et_from);
        TextView  persons=dialog.findViewById(R.id.et_to);
        TextView uids=dialog.findViewById(R.id.uidroom);
        uids.setText(uid);
        AppCompatButton btn=dialog.findViewById(R.id.bt_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loading.setVisibility(View.VISIBLE);
                c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy  'at' HH:mm:ss ");
                String posted_on = sdf.format(new Date());
                Toast.makeText(getContext(), message.getText().toString(), Toast.LENGTH_SHORT).show();
                if(!from.getText().toString().isEmpty() && !persons.getText().toString().isEmpty() )
                {
                    FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            count=snapshot.child("Total_Posts_Roommates").getValue(Integer.class);

                            String reference=null;
                            try {
                                reference=  snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("Roommate Reference").getValue(String.class);
                                if(!reference.equals(null))
                                {
                                    FirebaseDatabase.getInstance().getReference().child("Roommates Requests").child(reference).removeValue();

                                }
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            int k=count-1;

                            final int[] x = new int[1];
                            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    x[0] =snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("Element").getValue(Integer.class);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                    x[0]=3;
                                }
                            });
                            uidt=snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("College UID").getValue(String.class);
                            Roommate_request_post_data data=new Roommate_request_post_data(froms,tos,posted_on,message.getText().toString(),uidt,x[0]);
                            FirebaseDatabase.getInstance().getReference().child("Roommates Requests").child(count+uidt).setValue(data);
                            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Roommate Reference").setValue(count+uidt);
                            FirebaseDatabase.getInstance().getReference().child("Total_Posts_Roommates").setValue(k);
                            loading.setVisibility(View.GONE);
                            btn.setEnabled(false);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {
                    loading.setVisibility(View.GONE);
                }
            }
        });


        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ina = new ArrayList<String>();
                ina.add("Business Services");
                ina.add("Computers");
                ina.add("Education");
                ina.add("Personal");
                ina.add("Travel");
                dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.searchable_single_option_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText editText=dialog.findViewById(R.id.edittext);
                ListView listView=dialog.findViewById(R.id.listview);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,ina);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        from.setText(adapter.getItem(position));
                        froms=adapter.getItem(position);
                        dialog.dismiss();

                    }
                });
            }


        });

        persons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persona = new ArrayList<String>();
                persona.add("Business Services");
                persona.add("Computers");
                persona.add("Education");
                persona.add("Personal");
                persona.add("Travel");
                dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.searchable_single_option_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText editText=dialog.findViewById(R.id.edittext);

                ListView listView=dialog.findViewById(R.id.listview);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,persona);
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        persons.setText(adapter.getItem(position));
                        tos=adapter.getItem(position);
                        dialog.dismiss();

                    }
                });
            }

        });



    }
}