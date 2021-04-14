package com.example.sign_in_register.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sign_in_register.R;

public class emergencyFragment extends Fragment {

    ImageButton call;
    View view;
    TextView title,phone_num;
    int textsize;
    String textstyle;
    ImageView uploadButton;
    ImageView emergencyImage;

    public static final int PERMISSION_CODE = 1000;
    public static final int IMAGE_PICK = 2000;

    public emergencyFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_emergency, container, false);
        init();


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get the number
        String number = phone_num.getText().toString();
        phone_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make a call
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ number));
                startActivity(intent);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if permission has been granted
                if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    // If permission not granted, request it
                    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    getActivity().requestPermissions(permission, PERMISSION_CODE);
                }
                else {
                    // If permission granted
                    pickImageFromGallery();
                }
            }
        });
    }

    // cat our object, for now we set textsize as 30 but in future we get this data from server
    private void init()
    {
        title = (TextView)view.findViewById(R.id.Titile);
        phone_num = (TextView)view.findViewById(R.id.Phone_No);
        uploadButton = (ImageView)view.findViewById(R.id.Upload_Button);
        emergencyImage = (ImageView)view.findViewById(R.id.Emergency_Image);
        textsize = 30;
//        textstyle = "Time";

        title.setTextSize(textsize);
        phone_num.setTextSize(textsize);
    }

    public void pickImageFromGallery() {
        // Intent to pick image
        Intent getImage = new Intent(Intent.ACTION_PICK);
        getImage.setType("image/*");
        startActivityForResult(getImage,IMAGE_PICK);
    }

    // Handle permission during run time
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // If permission is being requested
        if(requestCode == PERMISSION_CODE) {
            // If permission has been granted
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();
            }
            // If permission denied
            else {
                Toast.makeText(getActivity(), "Permission required", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        getActivity();
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK) {
            emergencyImage.setImageURI(data.getData());
        }
    }
}