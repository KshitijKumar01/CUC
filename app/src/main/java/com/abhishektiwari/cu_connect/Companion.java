package com.abhishektiwari.cu_connect;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Companion extends Fragment  {
    RecyclerView recyclerView;
    Dialog dialog;
    String froms,tos;
    companion_adapter companion_adapter;
    DatePickerDialog.OnDateSetListener setListener;
    private TextView DateText;
    private int year, month, day;
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

        return view;
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