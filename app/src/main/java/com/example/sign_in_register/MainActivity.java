package com.example.sign_in_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    Button help;
    Button text;
    ImageView loginAudio;
    SoundPool soundPool;
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
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        loginSound = soundPool.load(this, R.raw.login, 1);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case(R.id.Login): {
                soundPool.release();
                Intent locSelect = new Intent(MainActivity.this, LocationSelectionActivity.class);
                startActivity(locSelect);
                break;
            }

            case(R.id.Help): {
                soundPool.release();
                Intent help = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(help);
                break;
            }

            case(R.id.Text_Size): {

            }

            case(R.id.LoginAudio): {
                soundPool.play(loginSound, 1, 1, 1, 0, 1);
            }
        }
    }
}