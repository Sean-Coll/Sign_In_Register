package com.example.sign_in_register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    Button help;
    Button text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.Login);
        help = findViewById(R.id.Help);
        text = findViewById(R.id.Text_Size);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case(R.id.Login): {

            }

            case(R.id.Help): {

            }

            case(R.id.Text_Size): {

            }
        }
    }
}