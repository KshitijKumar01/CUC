package com.abhishektiwari.cu_connect;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.RestrictionEntry;
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
import androidx.viewpager.widget.ViewPager;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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


public class Companion extends Fragment  {
    RecyclerView recyclerView;
    Dialog dialog,dialog2;
    Calendar c;
    int count;
    String from_state="",to_state="",from_city="",to_city="";
    private ArrayAdapter<CharSequence> stateadapter,districtAdapter,fromadp,toadp;
    String froms="",tos="",date="",uids="",date_search="";
    SharedPreferences sharedpreferences;
    CardView loading;
    ImageView  tv_search;
    companion_adapter companion_adapter;
    DatePickerDialog.OnDateSetListener setListener, setListener2;
    private TextView DateText,DateText2;
    private int year, month, day,year2, month2, day2;
    ImageView addrequest;
    ArrayList<companion_request_post_data> arrcompanion;

    public Companion() {
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
        View view= inflater.inflate(R.layout.fragment_companion, container, false);
        recyclerView=view.findViewById(R.id.companions_recycler);
        arrcompanion=new ArrayList<>();
        companion_adapter =new companion_adapter(getContext(),arrcompanion);
        recyclerView.setAdapter(companion_adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loading=view.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.child("Companion Requests").getChildren()) {

                    try {
                        companion_request_post_data cr=dataSnapshot.getValue(companion_request_post_data.class);
                        arrcompanion.add(cr);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                companion_adapter.notifyDataSetChanged();
                loading.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        addrequest=view.findViewById(R.id.addcompanionrequest);

        sharedpreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        uids = sharedpreferences.getString("Email", null);

        addrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpostdialog();
            }
        });
        tv_search = view.findViewById(R.id.btn_search);


        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchdialog();



            }
        });

        return view;
    }

    private void searchdialog() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog2 = new Dialog(getContext());
        dialog2.setContentView(R.layout.search_companion_layout);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.show();
        Spinner state_from=dialog2.findViewById(R.id.states_spinner_from);
        Spinner state_to=dialog2.findViewById(R.id.state_spinner_to);
        AppCompatButton bt_submit = dialog2.findViewById(R.id.bt_submit);

        stateadapter= ArrayAdapter.createFromResource(getContext(),R.array.arrey_indian_states,R.layout.spinner_layout);
        stateadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state_from.setAdapter(stateadapter);
        state_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                from_city = state_from.getSelectedItem().toString();
                Spinner district = dialog2.findViewById(R.id.city_spinner_from);
                int parentId = parent.getId();
                if (parentId == R.id.states_spinner_from) {
                    from_state = state_from.getSelectedItem().toString();      //Obtain the selected State
                    int parentID = parent.getId();
                    if (parentID == R.id.states_spinner_from) {
                        switch (from_state) {
                            case "Select Your State":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_default_districts, R.layout.spinner_layout);
                                break;
                            case "Andhra Pradesh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_andhra_pradesh_districts, R.layout.spinner_layout);
                                break;
                            case "Arunachal Pradesh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_arunachal_pradesh_districts, R.layout.spinner_layout);
                                break;
                            case "Assam":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_assam_districts, R.layout.spinner_layout);
                                break;
                            case "Bihar":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_bihar_districts, R.layout.spinner_layout);
                                break;
                            case "Chhattisgarh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_chhattisgarh_districts, R.layout.spinner_layout);
                                break;
                            case "Goa":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_goa_districts, R.layout.spinner_layout);
                                break;
                            case "Gujarat":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_gujarat_districts, R.layout.spinner_layout);
                                break;
                            case "Haryana":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_haryana_districts, R.layout.spinner_layout);
                                break;
                            case "Himachal Pradesh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_himachal_pradesh_districts, R.layout.spinner_layout);
                                break;
                            case "Jharkhand":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_jharkhand_districts, R.layout.spinner_layout);
                                break;
                            case "Karnataka":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_karnataka_districts, R.layout.spinner_layout);
                                break;
                            case "Kerala":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_kerala_districts, R.layout.spinner_layout);
                                break;
                            case "Madhya Pradesh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_madhya_pradesh_districts, R.layout.spinner_layout);
                                break;
                            case "Maharashtra":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_maharashtra_districts, R.layout.spinner_layout);
                                break;
                            case "Manipur":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_manipur_districts, R.layout.spinner_layout);
                                break;
                            case "Meghalaya":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_meghalaya_districts, R.layout.spinner_layout);
                                break;
                            case "Mizoram":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_mizoram_districts, R.layout.spinner_layout);
                                break;
                            case "Nagaland":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_nagaland_districts, R.layout.spinner_layout);
                                break;
                            case "Odisha":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_odisha_districts, R.layout.spinner_layout);
                                break;
                            case "Punjab":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_punjab_districts, R.layout.spinner_layout);
                                break;
                            case "Rajasthan":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_rajasthan_districts, R.layout.spinner_layout);
                                break;
                            case "Sikkim":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_sikkim_districts, R.layout.spinner_layout);
                                break;
                            case "Tamil Nadu":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_tamil_nadu_districts, R.layout.spinner_layout);
                                break;
                            case "Telangana":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_telangana_districts, R.layout.spinner_layout);
                                break;
                            case "Tripura":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_tripura_districts, R.layout.spinner_layout);
                                break;
                            case "Uttar Pradesh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_uttar_pradesh_districts, R.layout.spinner_layout);
                                break;
                            case "Uttarakhand":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_uttarakhand_districts, R.layout.spinner_layout);
                                break;
                            case "West Bengal":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_west_bengal_districts, R.layout.spinner_layout);
                                break;
                            case "Andaman and Nicobar Islands":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_andaman_nicobar_districts, R.layout.spinner_layout);
                                break;
                            case "Chandigarh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_chandigarh_districts, R.layout.spinner_layout);
                                break;
                            case "Dadra and Nagar Haveli":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_dadra_nagar_haveli_districts, R.layout.spinner_layout);
                                break;
                            case "Daman and Diu":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_daman_diu_districts, R.layout.spinner_layout);
                                break;
                            case "Delhi":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_delhi_districts, R.layout.spinner_layout);
                                break;
                            case "Jammu and Kashmir":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_jammu_kashmir_districts, R.layout.spinner_layout);
                                break;
                            case "Lakshadweep":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_lakshadweep_districts, R.layout.spinner_layout);
                                break;
                            case "Ladakh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_ladakh_districts, R.layout.spinner_layout);
                                break;
                            case "Puducherry":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_puducherry_districts, R.layout.spinner_layout);
                                break;
                            default:
                                break;
                        }

                        districtAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);     // Specify the layout to use when the list of choices appears
                        district.setAdapter(districtAdapter);        //Populate the list of Districts in respect of the State selected
                        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                from_city = district.getSelectedItem().toString();


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }


        });
        state_to.setAdapter(stateadapter);
        state_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                to_state = state_to.getSelectedItem().toString();
                Spinner district_to = dialog2.findViewById(R.id.city_spinner_to);
                int parentId = parent.getId();
                if (parentId == R.id.state_spinner_to) {
                    to_state = state_to.getSelectedItem().toString();      //Obtain the selected State
                    int parentID = parent.getId();
                    if (parentID == R.id.state_spinner_to) {
                        switch (to_state) {
                            case "Select Your State":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_default_districts, R.layout.spinner_layout);
                                break;
                            case "Andhra Pradesh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_andhra_pradesh_districts, R.layout.spinner_layout);
                                break;
                            case "Arunachal Pradesh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_arunachal_pradesh_districts, R.layout.spinner_layout);
                                break;
                            case "Assam":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_assam_districts, R.layout.spinner_layout);
                                break;
                            case "Bihar":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_bihar_districts, R.layout.spinner_layout);
                                break;
                            case "Chhattisgarh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_chhattisgarh_districts, R.layout.spinner_layout);
                                break;
                            case "Goa":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_goa_districts, R.layout.spinner_layout);
                                break;
                            case "Gujarat":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_gujarat_districts, R.layout.spinner_layout);
                                break;
                            case "Haryana":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_haryana_districts, R.layout.spinner_layout);
                                break;
                            case "Himachal Pradesh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_himachal_pradesh_districts, R.layout.spinner_layout);
                                break;
                            case "Jharkhand":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_jharkhand_districts, R.layout.spinner_layout);
                                break;
                            case "Karnataka":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_karnataka_districts, R.layout.spinner_layout);
                                break;
                            case "Kerala":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_kerala_districts, R.layout.spinner_layout);
                                break;
                            case "Madhya Pradesh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_madhya_pradesh_districts, R.layout.spinner_layout);
                                break;
                            case "Maharashtra":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_maharashtra_districts, R.layout.spinner_layout);
                                break;
                            case "Manipur":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_manipur_districts, R.layout.spinner_layout);
                                break;
                            case "Meghalaya":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_meghalaya_districts, R.layout.spinner_layout);
                                break;
                            case "Mizoram":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_mizoram_districts, R.layout.spinner_layout);
                                break;
                            case "Nagaland":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_nagaland_districts, R.layout.spinner_layout);
                                break;
                            case "Odisha":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_odisha_districts, R.layout.spinner_layout);
                                break;
                            case "Punjab":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_punjab_districts, R.layout.spinner_layout);
                                break;
                            case "Rajasthan":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_rajasthan_districts, R.layout.spinner_layout);
                                break;
                            case "Sikkim":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_sikkim_districts, R.layout.spinner_layout);
                                break;
                            case "Tamil Nadu":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_tamil_nadu_districts, R.layout.spinner_layout);
                                break;
                            case "Telangana":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_telangana_districts, R.layout.spinner_layout);
                                break;
                            case "Tripura":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_tripura_districts, R.layout.spinner_layout);
                                break;
                            case "Uttar Pradesh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_uttar_pradesh_districts, R.layout.spinner_layout);
                                break;
                            case "Uttarakhand":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_uttarakhand_districts, R.layout.spinner_layout);
                                break;
                            case "West Bengal":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_west_bengal_districts, R.layout.spinner_layout);
                                break;
                            case "Andaman and Nicobar Islands":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_andaman_nicobar_districts, R.layout.spinner_layout);
                                break;
                            case "Chandigarh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_chandigarh_districts, R.layout.spinner_layout);
                                break;
                            case "Dadra and Nagar Haveli":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_dadra_nagar_haveli_districts, R.layout.spinner_layout);
                                break;
                            case "Daman and Diu":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_daman_diu_districts, R.layout.spinner_layout);
                                break;
                            case "Delhi":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_delhi_districts, R.layout.spinner_layout);
                                break;
                            case "Jammu and Kashmir":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_jammu_kashmir_districts, R.layout.spinner_layout);
                                break;
                            case "Lakshadweep":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_lakshadweep_districts, R.layout.spinner_layout);
                                break;
                            case "Ladakh":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_ladakh_districts, R.layout.spinner_layout);
                                break;
                            case "Puducherry":
                                districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                        R.array.array_puducherry_districts, R.layout.spinner_layout);
                                break;
                            default:
                                break;
                        }

                        districtAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);     // Specify the layout to use when the list of choices appears
                        district_to.setAdapter(districtAdapter);        //Populate the list of Districts in respect of the State selected
                        district_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                to_city = district_to.getSelectedItem().toString();


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }


        });


        DateText2=dialog2.findViewById(R.id.date_picker2);
        TextView error=dialog2.findViewById(R.id.errortext);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setText("");
                if (from_state.equals("Select Your State") || to_state.equals("Select Your State")) {
                    Toast.makeText(getContext(), "Please select your state from the list", Toast.LENGTH_LONG).show();
                    error.setText("State is required !");
                    error.setError("state");
                    error.requestFocus();
                } else if (from_city.equals("Select Your District") || to_city.equals("Select Your District")) {

                    error.setText("District is required !");
                    error.setError("district");
                    error.requestFocus();

                } else {
                    searchnow(from_city,to_city,date_search);
                }

            }
        });
        DateText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year2 = calendar.get(Calendar.YEAR);
                month2 = calendar.get(Calendar.MONTH);
                day2 = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener2,year2, month2,day2);
                datePickerDialog.show();

            }
        });
        setListener2=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                date_search=dayOfMonth+"/"+month+"/"+year;
                DateText2.setText(date_search);
            }
        };




    }

    private void searchnow(String from, String to, String date) {
        arrcompanion.clear();
        dialog2.dismiss();
        loading.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.child("Companion Requests").getChildren()) {

                    try {
                        companion_request_post_data cr=dataSnapshot.getValue(companion_request_post_data.class);
                        if(cr.getFrom().equals(from) && cr.getTo().equals(to) && cr.getDate().equals(date))
                        {
                            arrcompanion.add(cr);
                        }

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                if(arrcompanion.isEmpty())
                {
                    recyclerView.setBackground(getResources().getDrawable(R.mipmap.search));
                }
                companion_adapter.notifyDataSetChanged();
                loading.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    void addpostdialog() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.companion_posts);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout((6 * width)/7, (4 * height)/7);
        DateText=dialog.findViewById(R.id.date_picker);
        dialog.show();
        EditText message=dialog.findViewById(R.id.type_text);
        TextView  from=dialog.findViewById(R.id.et_from);
        TextView  uid=dialog.findViewById(R.id.uid);
        AppCompatButton btn=dialog.findViewById(R.id.bt_submit);
        uid.setText(uids);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy  'at' HH:mm:ss ");
                String posted_on = sdf.format(new Date());
                Toast.makeText(getContext(), message.getText().toString(), Toast.LENGTH_SHORT).show();
                if(!froms.isEmpty() && !tos.isEmpty() && !date.isEmpty() )
                {
                    FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            count=snapshot.child("Total_Posts_Companions").getValue(Integer.class);

                            String reference=null;
                            try {
                                reference=  snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("Companion Reference").getValue(String.class);
                                if(!reference.equals(null))
                                {
                                    FirebaseDatabase.getInstance().getReference().child("Companion Requests").child(reference).removeValue();

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
                            uids=snapshot.child("users").child(FirebaseAuth.getInstance().getUid()).child("College UID").getValue(String.class);
                            companion_request_post_data data=new companion_request_post_data(froms,tos,date,uids,message.getText().toString(),posted_on,x[0]);
                            FirebaseDatabase.getInstance().getReference().child("Companion Requests").child(count+uids).setValue(data);
                            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Companion Reference").setValue(count+uids);
                            FirebaseDatabase.getInstance().getReference().child("Total_Posts_Companions").setValue(k);
                            btn.setEnabled(false);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.searchable_single_option_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                fromadp=ArrayAdapter.createFromResource(getContext(),R.array.all_cities,android.R.layout.simple_list_item_1);
                EditText editText=dialog.findViewById(R.id.edittext);
                ListView listView=dialog.findViewById(R.id.listview);
                listView.setAdapter(fromadp);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        fromadp.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        from.setText(fromadp.getItem(position));
                        froms=(fromadp.getItem(position)).toString();
                        dialog.dismiss();

                    }
                });
            }


        });
        TextView  to=dialog.findViewById(R.id.et_to);
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.searchable_single_option_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText editText=dialog.findViewById(R.id.edittext);

                ListView listView=dialog.findViewById(R.id.listview);
                toadp=ArrayAdapter.createFromResource(getContext(),R.array.all_cities,android.R.layout.simple_list_item_1);

                listView.setAdapter(toadp);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        toadp.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        to.setText(toadp.getItem(position));
                        tos=(toadp.getItem(position)).toString();
                        dialog.dismiss();

                    }
                });
            }

        });

        DateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year, month,day);
                datePickerDialog.show();

            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                date=dayOfMonth+"/"+month+"/"+year;
                DateText.setText(date);
            }
        };


    }


}