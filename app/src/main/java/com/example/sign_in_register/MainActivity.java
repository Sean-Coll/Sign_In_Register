package com.example.sign_in_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    Button login, help, text;
    TextView textDecrease, textIncrease;
    CustomSoundPool custSoundPool;
    int loginSound, textDecreaseSound, textIncreaseSound, helpSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.Login);
        help = findViewById(R.id.Help);
        text = findViewById(R.id.Text_Size);
        textDecrease = findViewById(R.id.SmallT);
        textIncrease = findViewById(R.id.BigT);

        // Set up onClick and onLongClick listeners
        setUpClickListeners(login);
        setUpClickListeners(help);
        setUpClickListeners(textDecrease);
        setUpClickListeners(textIncrease);
        setUpClickListeners(help);

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

    @Override
    public boolean onLongClick(View view) {
        switch(view.getId()) {

            case(R.id.Login): {
                custSoundPool.play(loginSound);
                break;
            }

            case(R.id.Help): {
                custSoundPool.play(helpSound);
                break;
            }

            case(R.id.Text_Size): {
                break;
            }

            case(R.id.SmallT): {
                custSoundPool.play(textDecreaseSound);
                break;
            }

            case(R.id.BigT): {
                custSoundPool.play(textIncreaseSound);
                break;
            }
        }
        return true;
    }

    // Sets up the onClick and onLongClick listeners for the View passed in
    public void setUpClickListeners(View view) {
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }
}