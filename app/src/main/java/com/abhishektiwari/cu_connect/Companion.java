package com.abhishektiwari.cu_connect;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Companion extends Fragment  {
    RecyclerView recyclerView;
    Dialog dialog,dialog2;
    String from_state,to_state,from_city,to_city;
    private ArrayAdapter<CharSequence> stateadapter,districtAdapter,stateadapter_to,districtAdapter_to;
    String froms,tos;
    TextView btn_okay, btn_cancel;
    ImageView  tv_search;
    companion_adapter companion_adapter;
    DatePickerDialog.OnDateSetListener setListener, setListener2;
    private TextView DateText,DateText2;
    private int year, month, day,year2, month2, day2;
    List<String> toa,froma;
    ImageView addrequest;

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
        companion_adapter =new companion_adapter(getContext());
        recyclerView.setAdapter(companion_adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addrequest=view.findViewById(R.id.addcompanionrequest);


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
                    Toast.makeText(getContext(), "Selected State: "+from_state+" to "+to_state+"\nSelected District: "+from_city+" to "+to_city, Toast.LENGTH_LONG).show();
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
                String date=dayOfMonth+"/"+month+"/"+year;
                DateText2.setText(date);
            }
        };




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
        AppCompatButton btn=dialog.findViewById(R.id.bt_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), message.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                froma = new ArrayList<String>();
                froma.add("Business Services");
                froma.add("Computers");
                froma.add("Education");
                froma.add("Personal");
                froma.add("Travel");
                dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.searchable_single_option_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText editText=dialog.findViewById(R.id.edittext);

                ListView listView=dialog.findViewById(R.id.listview);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,froma);
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
        TextView  to=dialog.findViewById(R.id.et_to);
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toa = new ArrayList<String>();
                toa.add("Business Services");
                toa.add("Computers");
                toa.add("Education");
                toa.add("Personal");
                toa.add("Travel");
                dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.searchable_single_option_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText editText=dialog.findViewById(R.id.edittext);

                ListView listView=dialog.findViewById(R.id.listview);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,toa);
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
                        to.setText(adapter.getItem(position));
                        tos=adapter.getItem(position);
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
                String date=dayOfMonth+"/"+month+"/"+year;
                DateText.setText(date);
            }
        };


    }


}