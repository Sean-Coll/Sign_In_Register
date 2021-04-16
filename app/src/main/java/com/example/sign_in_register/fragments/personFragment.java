package com.example.sign_in_register.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sign_in_register.ImageHandler;
import com.example.sign_in_register.R;

public class personFragment extends Fragment {

    View view;
    TextView name, age;
    ImageView timetableImage, uploadButton, profilePicture;
    ImageHandler imageHandler, profilePicHandler;

    public static final int PERMISSION_CODE = 1000;
    public static final int IMAGE_PICK = 2000;
    public static final int PROFILE_PICTURE_PICK = 3000;

    public personFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person, container, false);
        init();


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if permission to write to storage has been granted
                if(getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    // If permission not granted, request it
                    String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    getActivity().requestPermissions(permission, PERMISSION_CODE);
                }
                // Check if permission to read storage has been granted
                if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    // If permission not granted, request it
                    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    getActivity().requestPermissions(permission, PERMISSION_CODE);
                }
                else {
                    // If permission granted
                    pickImageFromGallery(IMAGE_PICK);
                }
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if permission to write to storage has been granted
                if(getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    // If permission not granted, request it
                    String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    getActivity().requestPermissions(permission, PERMISSION_CODE);
                }
                // Check if permission to read storage has been granted
                if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    // If permission not granted, request it
                    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    getActivity().requestPermissions(permission, PERMISSION_CODE);
                }
                else {
                    // If permission granted
                    pickImageFromGallery(PROFILE_PICTURE_PICK);
                }
            }
        });
        imageHandler.loadImage();
    }

    private void init()
    {
        name = (TextView)view.findViewById(R.id.Name);
        age = (TextView)view.findViewById(R.id.Age);
        uploadButton = (ImageView)view.findViewById(R.id.Upload_Button);
        timetableImage = (ImageView)view.findViewById(R.id.Timetable_Image);
        profilePicture = (ImageView)view.findViewById(R.id.Profile_Picture);

        imageHandler = new ImageHandler(timetableImage, "timetableImage.jpg");
        profilePicHandler = new ImageHandler(profilePicture, "profilePicture.jpg");
    }

    public void pickImageFromGallery(int id) {
        // Intent to pick image
        Intent getImage = new Intent(Intent.ACTION_PICK);
        getImage.setType("image/*");
        startActivityForResult(getImage,id);
    }

    // Handle permission during run time
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // If permission is being requested
        if(requestCode == PERMISSION_CODE) {
            // If permission has been granted
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getActivity(), "Permission required", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        getActivity();
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK) {
            timetableImage.setImageURI(data.getData());
            // Save the image
            if(!imageHandler.saveImage()) {
                Toast.makeText(getActivity(), "There was a problem saving.", Toast.LENGTH_LONG).show();
            }
        }
        else if(resultCode == Activity.RESULT_OK && requestCode == PROFILE_PICTURE_PICK) {
            profilePicture.setImageURI(data.getData());
            // Save the image
            if(!profilePicHandler.saveImage()) {
                Toast.makeText(getActivity(), "There was a problem saving.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        imageHandler.loadImage();
        profilePicHandler.loadImage();
    }
}