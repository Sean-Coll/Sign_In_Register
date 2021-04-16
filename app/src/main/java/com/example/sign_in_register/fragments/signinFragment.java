package com.example.sign_in_register.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sign_in_register.LoginVoiceActivity;
import com.example.sign_in_register.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class signinFragment extends Fragment {


    public signinFragment() {
        // Required empty public constructor
    }

    //create  objects
    View view;
    ImageButton QR_code,Voice_log;
    TextView CurTime;
    DateFormat df;
    ImageView greenTick;
    String signedInDate;

    public static final String PREFS_NAME = "PreferenceFile";
    public static final String DATE = "date";


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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        QR_code = (ImageButton)getActivity().findViewById(R.id.QR_sign_in);
        Voice_log = (ImageButton)getActivity().findViewById(R.id.Voice_Sign_in);
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
    }

    //for read tune i
    private void setTime()
    {
            CurTime = (TextView)view.findViewById(R.id.Curren_Time);
            df = new SimpleDateFormat("dd-MMM-yyyy");
            String curTime = df.format(new Date());
            CurTime.setText(curTime);
            CurTime.setTextSize(30);
    }

    // To react when the user signs in successfully
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
