/* This activity provides speech recognition to allow the user to log in from home. Most of the
 * functionality for this activity comes from SpeechRecognition.java.
 * Author: Seán Coll
 * Created: 12/3/21
 * Last Edited: 12/4/21
 */

package com.example.sign_in_register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class LoginVoiceActivity extends AppCompatActivity implements View.OnClickListener {

    SpeechRecognition speechRec; // The speech recognition object from SpeechRecognition.java
    ImageView back;
    ImageButton micIcon; // The trigger for speech recognition
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
        micIcon = findViewById(R.id.Mic_Icon);
        micIcon.setOnClickListener(this);
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
    // Validate the user's input, if its correct, sign them in
    public void validate(String result){
        if(result.toLowerCase().equals("hello")) {
            // Get today's date in the correct format to insert
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String date = df.format(new Date());
            DBOperations signIn = new DBOperations(this, "signIn");
            try {
                // The Name needs to come from the profile page
                String response = signIn.execute("Sean", "Coll", date).get();
                Toast.makeText(this,response,Toast.LENGTH_LONG).show();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
