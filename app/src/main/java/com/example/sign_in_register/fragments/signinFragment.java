package com.example.sign_in_register.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sign_in_register.ImageHandler;
import com.example.sign_in_register.LoginVoiceActivity;
import com.example.sign_in_register.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class signinFragment extends Fragment {


    public signinFragment() {
        // Required empty public constructor
    }

    //create objects
    View view;
    ImageButton QR_code,Voice_log;
    TextView CurTime;
    DateFormat df;
    ImageView greenTick;
    String signedInDate;
    ImageView uploadButton, dayCentreImage;
    ImageHandler imageHandler;

    public static final String PREFS_NAME = "PreferenceFile";
    public static final String DATE = "date";
    public static final int PERMISSION_CODE = 1000;
    public static final int IMAGE_PICK = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadDate();
        signedInDate = " "; // FOR DEBUGGING ONLY
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //  get everthing setup
        view = (View)inflater.inflate(R.layout.fragment_signin, container, false);
        setTime();
        init();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // These need to be initialised here since this is the starting fragment
        QR_code = (ImageButton)view.findViewById(R.id.QR_sign_in);
        Voice_log = (ImageButton)view.findViewById(R.id.Voice_Sign_in);
        uploadButton = view.findViewById(R.id.Upload_Button);
        dayCentreImage = view.findViewById(R.id.Day_Centre_Image);
        greenTick = getActivity().findViewById(R.id.Green_Tick);

        QR_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //should be qr scanning feature but for test set voice
                Intent pagechange = new Intent(getActivity(),LoginVoiceActivity.class);
                startActivity(pagechange);
            }
        });

        Voice_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personFragment pf = new personFragment();
                Intent pagechange = new Intent(getActivity(), LoginVoiceActivity.class);
                Log.i("USERNAME", pf.getUsername());
                pagechange.putExtra("uname", pf.username);
                startActivityForResult(pagechange, 1);
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
        imageHandler.loadImage();
    }

    public void init() {
        QR_code = (ImageButton)view.findViewById(R.id.QR_sign_in);
        Voice_log = (ImageButton)view.findViewById(R.id.Voice_Sign_in);
        uploadButton = view.findViewById(R.id.Upload_Button);
        dayCentreImage = view.findViewById(R.id.Day_Centre_Image);
        greenTick = getActivity().findViewById(R.id.Green_Tick);
        LinearLayout background = (LinearLayout)view.findViewById(R.id.Emergency);
        RelativeLayout upload_area = (RelativeLayout)view.findViewById(R.id.upload_area);

        SharedPreferences userTheme = getActivity().getSharedPreferences("Theme", Activity.MODE_PRIVATE);
        int cur_size = userTheme.getInt("FontSize", 12);
        int cur_fontstyle = userTheme.getInt("FontStyle",0);
        String cur_theme = userTheme.getString("ColorString","#FFFFFFFF");

        background.setBackgroundColor(Color.parseColor(cur_theme));
        upload_area.setBackgroundColor(Color.parseColor(cur_theme));

        CurTime.setTextSize(cur_size);
        CurTime.setTypeface(null, cur_fontstyle);

        imageHandler = new ImageHandler(dayCentreImage, "dayCentre.jpg");
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

    //for read tune i
    private void setTime()
    {
            CurTime = (TextView)view.findViewById(R.id.Current_Time);
            df = new SimpleDateFormat("dd-MMM-yyyy");
            String curTime = df.format(new Date());
            CurTime.setText(curTime);
            CurTime.setTextSize(30);
    }

    // To react when the user signs in successfully
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        // When the user selects a new image
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK) {
            dayCentreImage.setImageURI(data.getData());
            // Save the image
            if(!imageHandler.saveImage()) {
                Toast.makeText(getActivity(), "There was a problem saving.", Toast.LENGTH_LONG).show();
            }
        }
        // When the user signs in successfully
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            saveData();
            loadDate();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(checkSignIn()) {
            signedIn();
        }
        else {
            signedOut();
        }
        imageHandler.loadImage();
    }

    // Checks if user is signed in
    public boolean checkSignIn() {
        return signedInDate.equals(CurTime.getText().toString());
    }

    // Hides the sign in options and displays green tick so users cannot sign in again
    public void signedIn() {
        QR_code.setOnClickListener(null);
        QR_code.setAlpha(0f);
        Voice_log.setOnClickListener(null);
        Voice_log.setAlpha(0f);
        greenTick.setAlpha(1f);
    }

    // Displays the sign in options and hides green tick so users can sign in
    public void signedOut() {
        setUpClickListeners();
        QR_code.setAlpha(1f);
        Voice_log.setAlpha(1f);
        greenTick.setAlpha(0f);
    }

    // Sets up onClickListeners so the code does not need to be duplicated
    public void setUpClickListeners() {
        QR_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //should be qr scanning feature but for test set voice
                Intent pagechange = new Intent(getActivity(),LoginVoiceActivity.class);
                startActivity(pagechange);
            }
        });

        Voice_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pagechange = new Intent(getActivity(), LoginVoiceActivity.class);
                startActivityForResult(pagechange, 1);
            }
        });
    }

    public void saveData() {
        SharedPreferences sp = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(DATE, CurTime.getText().toString());

        editor.apply();
    }

    public void loadDate() {
        SharedPreferences sp = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        signedInDate = sp.getString(DATE, "");
    }
}
