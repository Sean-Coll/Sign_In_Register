/* This activity provides speech recognition to allow the user to log in from home. Most of the
 * functionality for this activity comes from SpeechRecognition.java.
 * Author: Seán Coll
 * Created: 12/3/21
 * Last Edited: 12/3/21
 */

package com.example.sign_in_register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginVoiceActivity extends AppCompatActivity implements View.OnClickListener {

    SpeechRecognition speechRec; // The speech recognition object from SpeechRecognition.java
    ImageView back;
    Button micIcon; // The trigger for speech recognition
    TextView speechOutput; // The recognised speech will be displayed here
    TextView tapMessage;
    int textsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_voice);

        back = findViewById(R.id.Back_Arrow);
        speechOutput = findViewById(R.id.Speech_Output);
        tapMessage = findViewById(R.id.Tap_Message_Voice);
        back.setOnClickListener(this);


        Intent reciever = getIntent();
        textsize = reciever.getIntExtra("textsize",12);

        tapMessage.setTextSize(textsize);

        speechOutput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(tapMessage.getText().toString().equals(" ")) {
                    tapMessage.setText(R.string.say_name);

                }
                else {
                    tapMessage.setText(R.string.tap_message_voice);
                    tapMessage.setTextSize(textsize);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
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

        switch(view.getId()) {
            case(R.id.Back_Arrow): {
                finish();
                break;
            }

            case(R.id.Mic_Icon): {
                // Begin speech recognition
                speechOutput.setText(" "); // Clear the TextView
                speechRec.startListening();
                tapMessage.setText(R.string.say_name);
                break;
            }
        }
    }
}
