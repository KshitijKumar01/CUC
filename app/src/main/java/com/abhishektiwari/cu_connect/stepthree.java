package com.abhishektiwari.cu_connect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class stepthree extends Fragment  {

    Spinner branches,semester,intrests;


    public stepthree() {
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
        View view= inflater.inflate(R.layout.fragment_stepthree, container, false);
        branches=view.findViewById(R.id.branch);
        semester=view.findViewById(R.id.semester);
        intrests=view.findViewById(R.id.intrests);


        // Spinner Drop down elements
        List<String> branchesa = new ArrayList<String>();
        branchesa.add("Branch");
        branchesa.add("Business Services");
        branchesa.add("Computers");
        branchesa.add("Education");
        branchesa.add("Personal");
        branchesa.add("Travel");

        List<String> semestera = new ArrayList<String>();
        semestera.add("Semester");
        semestera.add("1");
        semestera.add("2");
        semestera.add("3");
        semestera.add("4");
        semestera.add("5");
        semestera.add("6");
        semestera.add("7");
        semestera.add("8");
        semestera.add("9");
        semestera.add("10");

        List<String> intrestsa = new ArrayList<String>();
        intrestsa.add("Intrests");
        intrestsa.add("1");
        intrestsa.add("2");
        intrestsa.add("3");
        intrestsa.add("4");
        intrestsa.add("5");
        intrestsa.add("6");
        intrestsa.add("7");
        intrestsa.add("8");
        intrestsa.add("9");
        intrestsa.add("10");

        // Creating adapter for spinner
        ArrayAdapter<String> branchesadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, branchesa);
        ArrayAdapter<String> semesteradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, semestera);
        ArrayAdapter<String> intrestsadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_multiple_choice, intrestsa);

        // Drop down layout style - list view with radio button
        branchesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesteradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intrestsadapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);

        // attaching data adapter to spinner
        branches.setAdapter(branchesadapter);
        semester.setAdapter(semesteradapter);
        intrests.setAdapter(intrestsadapter);

        branches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), branchesa.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), semestera.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        intrests.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), intrestsa.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return  view;
    }


}