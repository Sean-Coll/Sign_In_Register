package com.example.sign_in_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login, help, text;
    ImageView loginAudio, textDecreaseAudio, textIncreaseAudio, helpAudio;
    CustomSoundPool custSoundPool;
    int loginSound, textDecreaseSound, textIncreaseSound, helpSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.Login);
        help = findViewById(R.id.Help);
        text = findViewById(R.id.Text_Size);
        loginAudio = findViewById(R.id.LoginAudio);
        textDecreaseAudio = findViewById(R.id.SmallTAudio);
        textIncreaseAudio = findViewById(R.id.BigTAudio);
        helpAudio = findViewById(R.id.HelpAudio);
        login.setOnClickListener(this);
        help.setOnClickListener(this);
        text.setOnClickListener(this);
        loginAudio.setOnClickListener(this);
        textDecreaseAudio.setOnClickListener(this);
        textIncreaseAudio.setOnClickListener(this);
        helpAudio.setOnClickListener(this);

        // SoundPool creation and loading
        setUpSoundPool();
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
                break;
            }

            case(R.id.LoginAudio): {
                custSoundPool.play(loginSound);
                break;
            }

            case(R.id.SmallTAudio): {
                custSoundPool.play(textDecreaseSound);
                break;
            }

            case(R.id.BigTAudio): {
                custSoundPool.play(textIncreaseSound);
                break;
            }

            case(R.id.HelpAudio): {
                custSoundPool.play(helpSound);
                break;
            }
        }
    }

    // Sets up the SoundPool and loads sounds
    public void setUpSoundPool() {
        custSoundPool = new CustomSoundPool();
        custSoundPool.setCon(this);
        custSoundPool.initialise();

        loginSound = custSoundPool.load(R.raw.login);
        textDecreaseSound = custSoundPool.load(R.raw.decrease_text_size);
        textIncreaseSound = custSoundPool.load(R.raw.increase_text_size);
        helpSound = custSoundPool.load(R.raw.help);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setUpSoundPool(); // Re-create the SoundPool
    }
}