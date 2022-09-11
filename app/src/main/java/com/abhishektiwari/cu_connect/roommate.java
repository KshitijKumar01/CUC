package com.abhishektiwari.cu_connect;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class roommate extends Fragment {


    RecyclerView recyclerView;
    Dialog dialog;
    String froms,tos;
    roommate_adapter roommate_adapter;
    List<String> ina,persona;
    ImageView addrequest;

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
        roommate_adapter =new roommate_adapter(getContext());
        recyclerView.setAdapter(roommate_adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.roommates_posts);



        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout((6 * width)/7, (4 * height)/7);
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
        TextView  to=dialog.findViewById(R.id.et_to);
        to.setOnClickListener(new View.OnClickListener() {
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
                        to.setText(adapter.getItem(position));
                        tos=adapter.getItem(position);
                        dialog.dismiss();

                    }
                });
            }

        });



    }
}