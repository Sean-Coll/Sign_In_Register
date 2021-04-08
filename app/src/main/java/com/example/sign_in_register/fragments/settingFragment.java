package com.example.sign_in_register.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sign_in_register.AdminPage;
import com.example.sign_in_register.R;

public class settingFragment extends Fragment {

    ImageView logo;
    int numTaps; // The number of times the user has tapped the logo.

    public settingFragment() {
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
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        numTaps = 0;
        logo = getActivity().findViewById(R.id.SJOG_Logo);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numTaps++;
                if(numTaps >= 3) {
                    Intent admin = new Intent(getActivity(), AdminPage.class);
                    startActivity(admin);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        numTaps = 0;
    }
}