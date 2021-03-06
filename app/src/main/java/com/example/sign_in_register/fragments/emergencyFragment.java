package com.example.sign_in_register.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sign_in_register.CustomSoundPool;
import com.example.sign_in_register.ImageHandler;
import com.example.sign_in_register.R;

public class emergencyFragment extends Fragment {

    View view;
    TextView title,phone_num;
    int textsize;
    String textstyle;
    ImageView uploadButton, emergencyImage;
    ImageHandler imageHandler;
    CustomSoundPool custSoundPool;

    public static final int PERMISSION_CODE = 1000;
    public static final int IMAGE_PICK = 2000;
    int image_upload_sound, phone_dial_sound;

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

        phone_num.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                custSoundPool.play(phone_dial_sound);
                return true;
            }
        });

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
                    pickImageFromGallery();
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
        imageHandler.loadImage();
    }

    // cat our object, for now we set textsize as 30 but in future we get this data from server
    private void init()
    {
        title = (TextView)view.findViewById(R.id.Titile);
        phone_num = (TextView)view.findViewById(R.id.Phone_No);
        uploadButton = (ImageView)view.findViewById(R.id.Upload_Button);
        emergencyImage = (ImageView)view.findViewById(R.id.Emergency_Image);
        LinearLayout background = (LinearLayout)view.findViewById(R.id.Emergency);


        SharedPreferences userTheme = getActivity().getSharedPreferences("Theme", Activity.MODE_PRIVATE);
        int cur_size = userTheme.getInt("FontSize", 12);
        int cur_fontstyle = userTheme.getInt("FontStyle",0);
        String cur_theme = userTheme.getString("ColorString","#FFFFFFFF");

        background.setBackgroundColor(Color.parseColor(cur_theme));

        title.setTextSize(cur_size);
        title.setTypeface(null, cur_fontstyle);

        phone_num.setTextSize(cur_size);
        phone_num.setTypeface(null, cur_fontstyle);

        setUpSoundPool();

        imageHandler = new ImageHandler(emergencyImage, "emergencyImage.jpg");
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
            // Save the image
            if(!imageHandler.saveImage()) {
                Toast.makeText(getActivity(), "There was a problem saving.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        imageHandler.loadImage();
    }

    // Sets up the SoundPool and loads sounds
    public void setUpSoundPool() {
        custSoundPool = new CustomSoundPool();
        custSoundPool.setCon(getContext());
        custSoundPool.initialise();

        image_upload_sound = custSoundPool.load(R.raw.emergency_image_upload_desc);
        phone_dial_sound = custSoundPool.load(R.raw.tap_to_dial);
    }
}