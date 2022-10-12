package com.abhishektiwari.cu_connect;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class stepthree extends Fragment  {

    TextView branches,semester,intrests;
    SharedPreferences sharedpreferences;
    Dialog dialog;
    stepsindicator s;
    CardView next;
    LottieAnimationView lottieAnimationView;
    int i=0;
    ToastClass t;
    String Email;
    String uid;
    String semestert,brancht;
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
        t=new ToastClass(getContext());
        semester=view.findViewById(R.id.semester);
        intrests=view.findViewById(R.id.intrests);
        lottieAnimationView=view.findViewById(R.id.loadinganimation);
        lottieAnimationView.setVisibility(View.INVISIBLE);
        s=new stepsindicator();
        s.setI(3);

        sharedpreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        uid=sharedpreferences.getString("Uid",null);
        Email=sharedpreferences.getString("Email",null);
        // Spinner Drop down elements
        List<String> branchesa = new ArrayList<String>();
        branchesa.add("Business Services");
        branchesa.add("Computers");
        branchesa.add("Education");
        branchesa.add("Personal");
        branchesa.add("Travel");

        List<String> semestera = new ArrayList<String>();
        semestera.add("semester  1");
        semestera.add("semester  2");
        semestera.add("semester  3");
        semestera.add("semester  4");
        semestera.add("semester  5");
        semestera.add("semester  6");
        semestera.add("semester  7");
        semestera.add("semester  8");
        semestera.add("semester  9");
        semestera.add("semester  10");

        final String[] listItems = new String[]{"C", "C++", "JAVA", "PYTHON"};
        final boolean[] checkedItems = new boolean[listItems.length];
        final List<String> selectedItems = Arrays.asList(listItems);

        intrests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                intrests.setText(null);




                // initialise the alert dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set the title for the alert dialog
                builder.setTitle("Choose Items");

                // set the icon for the alert dialog
                builder.setIcon(R.mipmap.nonbglogo);

                // now this is the function which sets the alert dialog for multiple item selection ready
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which] = isChecked;
                        String currentItem = selectedItems.get(which);
                    }
                });

                // alert dialog shouldn't be cancellable
                builder.setCancelable(false);

                // handle the positive button of the dialog
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            if (checkedItems[i]) {

                                intrests.setText(intrests.getText() + selectedItems.get(i) + "  ");
                            }
                        }
                        if(checkedItems.length>0)
                        {
                            i=1;
                        }
                        else
                        {
                            i=0;
                        }

                    }
                });

                // handle the negative button of the alert dialog
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // handle the neutral button of the dialog to clear
                // the selected items boolean checkedItem
                builder.setNeutralButton("CLEAR ALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;

                        }
                        i=0;
                    }
                });

                // create the builder
                builder.create();

                // create the alert dialog with the
                // alert dialog builder instance
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });



        semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.searchable_single_option_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText editText=dialog.findViewById(R.id.edittext);

                ListView listView=dialog.findViewById(R.id.listview);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,semestera);
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
                        semester.setText(adapter.getItem(position));

                        semestert=adapter.getItem(position);
                        dialog.dismiss();
                    }
                });
            }

        });
        branches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.searchable_single_option_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText editText=dialog.findViewById(R.id.edittext);

                ListView listView=dialog.findViewById(R.id.listview);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,branchesa);
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
                        branches.setText(adapter.getItem(position));
                        brancht=adapter.getItem(position);
                        dialog.dismiss();

                    }
                });
            }
        });

        next=view.findViewById(R.id.next);
        next.setVisibility(View.VISIBLE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==1 && !brancht.isEmpty() && !semestert.isEmpty())
                {
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.VISIBLE);

                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Semester").setValue(semestert);
                    editor.putString("Semester",semestert);
                    editor.putString("Branch",brancht);
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Branch").setValue(brancht);


                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Steps Completed").setValue(3);
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Total Posts").setValue(0);

                    for (int i = 0; i < checkedItems.length; i++) {
                        if (checkedItems[i]) {

                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Intrests").child(String.valueOf(i)).setValue(selectedItems.get(i));

                        }
                    }
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Intrests").child(String.valueOf(checkedItems.length+1)).setValue("others");

                    Random rm=new Random();
                    int x=rm.nextInt(5);
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Element").setValue(x);


                    FirebaseDatabase.getInstance().getReference().child("Profile info").child(Email).child("Likes").setValue(0);
                    FirebaseDatabase.getInstance().getReference().child("Profile info").child(Email).child("Followers").setValue(0);
                    FirebaseDatabase.getInstance().getReference().child("Profile info").child(Email).child("Following").setValue(0);




                    try {
                        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                        s.setI(4);
                        fragmentManager.beginTransaction().setCustomAnimations(
                                // exit
                                R.anim.slide_up,   // popEnter
                                R.anim.slide_down  // popExit
                        ).replace(R.id.logsincon,new stepfour()).commit();

                    }catch (Exception e)
                    {

                    }

                }
                else
                {
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.VISIBLE);
                    t.errortoast("All fields are compulsory");

                }

            }
        });


        return  view;
    }


}