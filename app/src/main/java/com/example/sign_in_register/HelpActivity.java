package com.example.sign_in_register;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    TextView phoneNo;

    private static final int PHONE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_page);

        back = findViewById(R.id.Back_Arrow);
        phoneNo = findViewById(R.id.Phone_No);
        back.setOnClickListener(this);
        phoneNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case(R.id.Back_Arrow): {
                finish();
                break;
            }

            case(R.id.Phone_No): {
                checkPermission();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phoneNo.getText().toString()));
                startActivity(callIntent);
                break;
            }
        }
    }

    public void checkPermission()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE}, PHONE_PERMISSION);
    }
}
