package com.example.sign_in_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    SeekBar seekbar;
    int loginSound, textDecreaseSound, textIncreaseSound, helpSound, sizenow;
    CustomSoundPool custSoundPool;
    boolean inshow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button)findViewById(R.id.Login);
        Button help = (Button)findViewById(R.id.Help);
        Button text_size_change = (Button) findViewById(R.id.Textsize);

        //for now test 12 as default, we will store and read this dataset on server
        sizenow = 12;

        // Set up onClick and onLongClick listeners
        setUpClickListeners(login);
        setUpClickListeners(help);
        setUpClickListeners(text_size_change);

        login.setTextSize(sizenow);
        help.setTextSize(sizenow);
        text_size_change.setTextSize(sizenow);

        seekbar = (SeekBar)findViewById(R.id.textchange);
        seekbar.setProgress(sizenow);
        seekbar.setVisibility(View.GONE);    //hide seek bar as default


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                login.setTextSize(seekbar.getProgress());
                help.setTextSize(seekbar.getProgress());
                text_size_change.setTextSize(seekbar.getProgress());
                sizenow = seekbar.getProgress();
                seekbar.setProgress(sizenow);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //start move but its not necessary
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //make toast to show the size after changing
                Toast size_now = Toast.makeText(getApplicationContext(), "Text size now are: "+sizenow,Toast.LENGTH_SHORT);
                size_now.show();
                seekbar.setVisibility(View.GONE);
                inshow = false;
            }
        });


    }

    @Override
    public void onClick(View view) {

        // SoundPool creation and loading
        setUpSoundPool();
        switch(view.getId()) {

            //origin code changed for test loged page : Intent locSelect = new Intent(MainActivity.this, LocationSelectionActivity.class);
            case(R.id.Login): {
                custSoundPool.release();
                Intent locSelect = new Intent(MainActivity.this, MainPage.class);
                locSelect.putExtra("textsize",sizenow);
                startActivity(locSelect);
                break;
            }

            case(R.id.Help): {
                custSoundPool.release();
                Intent helps = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(helps);
                break;
            }

            case(R.id.Textsize): {

                if(inshow == false)
                {
                    seekbar.setVisibility(View.VISIBLE);    //show seek bar
                    inshow = true;
                }else  {
                    seekbar.setVisibility(View.GONE);    //hide seek bar
                    inshow = false;
                }

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
        }
        return true;
    }

    // Sets up the onClick and onLongClick listeners for the View passed in
    public void setUpClickListeners(View view) {
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }
}