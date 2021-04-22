/* This activity provides speech recognition to allow the user to log in from home. Most of the
 * functionality for this activity comes from SpeechRecognition.java.
 * Author: Seán Coll
 * Created: 12/3/21
 * Last Edited: 14/4/21
 */

package com.example.sign_in_register;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class LoginVoiceActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    SpeechRecognition speechRec; // The speech recognition object from SpeechRecognition.java
    ImageView back;
    ImageButton micIcon; // The trigger for speech recognition
    TextView speechOutput; // The recognised speech will be displayed here
    TextView tapMessage;
    CustomSoundPool custSoundPool;
    int textsize, mic_button_sound, back_arrow_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_voice);

        back = findViewById(R.id.Back_Arrow);
        speechOutput = findViewById(R.id.Speech_Output);
        tapMessage = findViewById(R.id.Tap_Message_Voice);
        micIcon = findViewById(R.id.Mic_Icon);
        micIcon.setOnClickListener(this);
        micIcon.setOnLongClickListener(this);
        back.setOnClickListener(this);
        back.setOnLongClickListener(this);

        setUpSoundPool();

        Intent reciever = getIntent();
        textsize = reciever.getIntExtra("textsize",30);

        tapMessage.setTextSize(textsize);
        speechOutput.setTextSize(textsize);

        speechOutput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(tapMessage.getText().toString().equals(" ")) {
                    tapMessage.setText(R.string.say_hello);

                }
                else {
                    tapMessage.setText(R.string.tap_message_voice);
                    tapMessage.setTextSize(textsize);
                }
            }
            // Every time the text changes, check if its valid
            @Override
            public void afterTextChanged(Editable editable) {
                // Don't check if the TextView is empty
                if(speechOutput.getText() != "") {
                    validate(speechOutput.getText().toString());
                }
            }
        });

        // Set up speechRec
        speechRec = new SpeechRecognition();
        speechRec.createObjects(this);
        speechRec.checkPermission(this,this);
        speechRec.setSpeechOutput(speechOutput);
        speechRec.setSpeechTrigger(micIcon);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case (R.id.Back_Arrow): {
                finish();
                break;
            }

            case (R.id.Mic_Icon): {
                // Begin speech recognition
                speechOutput.setText(" "); // Clear the TextView
                speechRec.startListening();
                tapMessage.setText(R.string.say_hello);
                break;
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch(view.getId()) {
            case(R.id.Back_Arrow): {
                custSoundPool.play(back_arrow_sound);
                break;
            }
            case(R.id.Mic_Icon): {
                custSoundPool.play(mic_button_sound);
                break;
            }
        }
        return true;
    }

    // Validate the user's input, if its correct, sign them in
    public void validate(String result){
        if(result.toLowerCase().equals("hello")) {
            String response = "";
            // Get username from profile page
            SharedPreferences profile = getSharedPreferences("Profile", Activity.MODE_PRIVATE);
            String username = profile.getString("Name", "");
            // Get today's date in the correct format to insert
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String date = df.format(new Date());
            DBOperations signIn = new DBOperations(this, "signIn");
            try {
                // The Name needs to come from the profile page
                response = signIn.execute(username, date).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            // If the user signs in successfully
            if(response.equals("Login Successful")) {
                setResult(RESULT_OK);
                finish();
            }
            // If the server is down
            else if(response.equals("Server is unavailable")) {
                tapMessage.setText("");
                tapMessage.setText(response);
            }
        }
    }

    // Sets up the SoundPool and loads sounds
    public void setUpSoundPool() {
        custSoundPool = new CustomSoundPool();
        custSoundPool.setCon(this);
        custSoundPool.initialise();

        mic_button_sound = custSoundPool.load(R.raw.mic_button_desc);
        back_arrow_sound = custSoundPool.load(R.raw.go_back);
    }
}
