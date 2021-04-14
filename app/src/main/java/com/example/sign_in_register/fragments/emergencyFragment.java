package com.example.sign_in_register.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sign_in_register.R;

public class emergencyFragment extends Fragment {

    ImageButton call;
    View view;
    TextView title,phone_num;
    int textsize;
    String textstyle;
    ImageView uploadButton;

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

            }
        });
    }

    // cat our object, for now we set textsize as 30 but in future we get this data from server
    private void init()
    {
        title = (TextView)view.findViewById(R.id.Titile);
        phone_num = (TextView)view.findViewById(R.id.Phone_No);
        uploadButton = (ImageView)view.findViewById(R.id.Upload_Button);
        textsize = 30;
//        textstyle = "Time";

        title.setTextSize(textsize);
        phone_num.setTextSize(textsize);
    }
}