package com.example.sign_in_register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LocationSelectionActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    Button saintJ;
    Button house;

   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.homeorcampus);

       back = findViewById(R.id.Back_Arrow);
       saintJ = findViewById(R.id.SaintJohn);
       house = findViewById(R.id.House);
       back.setOnClickListener(this);
       saintJ.setOnClickListener(this);
       house.setOnClickListener(this);
   }

    @Override
    public void onClick(View view) {

       switch(view.getId()) {

           case(R.id.Back_Arrow): {
               Intent back = new Intent(LocationSelectionActivity.this, MainActivity.class);
               startActivity(back);
               break;
           }

           case(R.id.SaintJohn): {

           }

           case(R.id.House): {

           }
       }
    }
}
