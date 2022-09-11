package com.abhishektiwari.cu_connect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.Arrays;
import java.util.List;

public class Post_for_home extends AppCompatActivity {

    ImageView imagepicker;
    EditText et_post;
    int i = 0;
    AppCompatButton bt_submit;
    ImageButton bt_photo, bt_link, bt_setting;
    TextView category_button;

    String cameraPermission[];
    String storagePermission[];
    Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_for_home);

        imagepicker = findViewById(R.id.imagepickerimage);
        et_post = findViewById(R.id.et_post);
        bt_submit = findViewById(R.id.bt_submit);
        bt_photo = findViewById(R.id.bt_photo);
        bt_link = findViewById(R.id.bt_link);
        bt_setting = findViewById(R.id.bt_setting);
        category_button = findViewById(R.id.category_post);
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category_button.setText(null);
                final String[] listItems = new String[]{"C", "C++", "JAVA", "PYTHON"};
                final boolean[] checkedItems = new boolean[listItems.length];
                final List<String> selectedItems = Arrays.asList(listItems);
                AlertDialog.Builder builder = new AlertDialog.Builder(Post_for_home.this);

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


                                category_button.setText(category_button.getText() + selectedItems.get(i) + "  ");
                            }
                        }
                        if (checkedItems.length > 0) {
                            i = 1;
                        } else {
                            i = 0;
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
                        i = 0;
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

        bt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.Companion.with(Post_for_home.this).compress(150)
                        .crop().maxResultSize(1080,1080).start(101);
            }
        });

        bt_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Post_for_home.this, "ATTACH", Toast.LENGTH_SHORT).show();
            }
        });

        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Post_for_home.this, "MENU", Toast.LENGTH_SHORT).show();
            }
        });

        et_post.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bt_submit.setEnabled(et_post.length() > 0);

                bt_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Post_for_home.this, et_post.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageuri=data.getData();
        imagepicker.setImageURI(imageuri);

    }
}