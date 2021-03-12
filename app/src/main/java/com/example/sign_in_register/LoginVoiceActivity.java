/* This activity provides speech recognition to allow the user to log in from home. Most of the
 * functionality for this activity comes from SpeechRecognition.java.
 * Author: Seán Coll
 * Created: 12/3/21
 * Last Edited: 12/3/21
 */

package com.example.sign_in_register;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginVoiceActivity extends AppCompatActivity implements View.OnClickListener {

    SpeechRecognition speechRec; // The speech recognition object from SpeechRecognition.java
    ImageView back;
    ImageView micIcon; // The trigger for speech recognition
    TextView speechOutput; // The recognised speech will be displayed here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_voice);

        back = findViewById(R.id.Back_Arrow);
        micIcon = findViewById(R.id.Mic_Icon);
        speechOutput = findViewById(R.id.Speech_Output);
        back.setOnClickListener(this);
        micIcon.setOnClickListener(this);

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
                speechRec.startListening();
                break;
            }
        }
    }
}
