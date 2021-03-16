package com.example.sign_in_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    Button help;
    Button text;
    ImageView loginAudio;
    CustomSoundPool custSoundPool;
    int loginSound, textIncreaseSound, textDecreaseSound, helpSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.Login);
        help = findViewById(R.id.Help);
        text = findViewById(R.id.Text_Size);
        loginAudio = findViewById(R.id.LoginAudio);
        login.setOnClickListener(this);
        help.setOnClickListener(this);
        text.setOnClickListener(this);
        loginAudio.setOnClickListener(this);

        // SoundPool creation and loading
        custSoundPool = new CustomSoundPool();
        custSoundPool.setCon(this);
        custSoundPool.initialise();

        loginSound = custSoundPool.load(R.raw.login);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case(R.id.Login): {
                custSoundPool.release();
                Intent locSelect = new Intent(MainActivity.this, LocationSelectionActivity.class);
                startActivity(locSelect);
                break;
            }

            case(R.id.Help): {
                custSoundPool.release();
                Intent help = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(help);
                break;
            }

            case(R.id.Text_Size): {

            }

            case(R.id.LoginAudio): {
                custSoundPool.play(loginSound);
            }
        }
    }
}