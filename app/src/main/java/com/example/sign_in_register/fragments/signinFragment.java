package com.example.sign_in_register.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        QR_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shoule be qr scanning feature but for test set voice
                Intent pagechange = new Intent(getActivity(),LoginVoiceActivity.class);
                startActivity(pagechange);
            }
        });

        Voice_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pagechange = new Intent(getActivity(), LoginVoiceActivity.class);
                startActivity(pagechange);
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
}
