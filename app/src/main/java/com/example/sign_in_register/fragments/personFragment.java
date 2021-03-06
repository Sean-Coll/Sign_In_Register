package com.example.sign_in_register.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sign_in_register.CustomSoundPool;
import com.example.sign_in_register.ImageHandler;
import com.example.sign_in_register.R;

public class personFragment extends Fragment {

    View view;
    TextView name, age;
    ImageView timetableImage, uploadButton, profilePicture;
    ImageHandler imageHandler, profilePicHandler;
    EditText nameEdit, ageEdit;
    CustomSoundPool custSoundPool;

    public String getUsername() {
        return username;
    }

    public String username;
    public static final int PERMISSION_CODE = 1000;
    public static final int IMAGE_PICK = 2000;
    public static final int PROFILE_PICTURE_PICK = 3000;
    int image_upload_sound, profile_picture_upload_sound;

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
        uploadButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                custSoundPool.play(image_upload_sound);
                return true;
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
        profilePicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                custSoundPool.play(profile_picture_upload_sound);
                return true;
            }
        });
        imageHandler.loadImage();
    }

    private void init()
    {
        name = (TextView)view.findViewById(R.id.Name);
        age = (TextView)view.findViewById(R.id.Age);

        nameEdit = (EditText)view.findViewById(R.id.Name_Edit);
        ageEdit = (EditText)view.findViewById(R.id.Age_Edit);
        uploadButton = (ImageView)view.findViewById(R.id.Upload_Button);
        timetableImage = (ImageView)view.findViewById(R.id.Timetable_Image);
        profilePicture = (ImageView)view.findViewById(R.id.Profile_Picture);
        LinearLayout background = (LinearLayout)view.findViewById(R.id.Profile);

        setUpSoundPool();

        SharedPreferences userTheme = getActivity().getSharedPreferences("Theme", Activity.MODE_PRIVATE);
        int cur_size = userTheme.getInt("FontSize", 12);
        int cur_fontstyle = userTheme.getInt("FontStyle",0);
        String cur_theme = userTheme.getString("ColorString","#FFFFFFFF");

        background.setBackgroundColor(Color.parseColor(cur_theme));
        name.setTextSize(cur_size);
        name.setTypeface(null, cur_fontstyle);

        age.setTextSize(cur_size);
        age.setTypeface(null, cur_fontstyle);

        nameEdit.setTextSize(cur_size);
        nameEdit.setTypeface(null, cur_fontstyle);
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SharedPreferences profile = getActivity().getSharedPreferences("Profile", Activity.MODE_PRIVATE);
                SharedPreferences.Editor profEditor = profile.edit();
                profEditor.putString("Name", nameEdit.getText().toString());
                profEditor.apply();
            }
        });
       // nameEdit.setBackgroundColor(Color.parseColor("#FFFFFFFF"));   use to change the background of edittext

        ageEdit.setTextSize(cur_size);
        ageEdit.setTypeface(null, cur_fontstyle);
        ageEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // If its not blank
                if(!ageEdit.getText().toString().equals("")) {
                    SharedPreferences profile = getActivity().getSharedPreferences("Profile", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor profEditor = profile.edit();
                    profEditor.putInt("Age", Integer.parseInt(String.valueOf(ageEdit.getText())));
                    profEditor.apply();
                }
                else {
                    SharedPreferences profile = getActivity().getSharedPreferences("Profile", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor profEditor = profile.edit();
                    profEditor.putInt("Age", 0);
                    profEditor.apply();
                }
            }
        });
        // ageEdit.setBackgroundColor(Color.parseColor("#FFFFFFFF"));   use to change the background of edittext


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
        username = name.getText().toString();
        loadDetails();
    }

    public void loadDetails() {
        SharedPreferences profile = getActivity().getSharedPreferences("Profile", Activity.MODE_PRIVATE);
        String profName = profile.getString("Name", "");
        int profAge = profile.getInt("Age", 0);
        nameEdit.setText(profName);
        ageEdit.setText(Integer.toString(profAge));

    }

    // Sets up the SoundPool and loads sounds
    public void setUpSoundPool() {
        custSoundPool = new CustomSoundPool();
        custSoundPool.setCon(getContext());
        custSoundPool.initialise();

        image_upload_sound = custSoundPool.load(R.raw.timetable_image_upload_desc);
        profile_picture_upload_sound = custSoundPool.load(R.raw.profile_picture_upload_desc);
    }
}