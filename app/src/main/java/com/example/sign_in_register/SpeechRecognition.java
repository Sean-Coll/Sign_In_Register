/* This class provides the use of speech recognition in a simplified way. All of the objects are
 *  are set up once createObjects() is called but the target TextView for output and the ImageView
 *  trigger needs to be set using the respective set methods.
 *
 *  The method order to start listening is:
 *  SpeechRecognition() to create the SpeechRecognition object (default constructor)
 *  createObjects(Context) to initialise the SpeechRecognizer, RecognizerIntent and
 *  RecognitionListener
 *  checkPermission(Context, Activity) checks if the app has the correct permission
 *  setSpeechOutput(TextView) sets the TextView to use for output
 *  setSpeechTrigger(ImageView) sets the ImageView that acts as the trigger
 *  startListening() To begin listening and then the output will be displayed
 *
 *  Author: Seán Coll
 *  Created: 6/3/21
 *  Last Edited: 7/3/21
 * */

package com.example.sign_in_register;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SpeechRecognition {

    private TextView speechOutput; // TextView where the recognised speech will be displayed
    private Button speechTrigger; // Button that will trigger the startListening() method
    private SpeechRecognizer speechRec; // Main SpeechRecognizer object that does the listening
    private Intent recIntent; // The Intent that supports the recogniser

    private static final int AUDIO_PERMISSION = 1; // Used when requesting permission

    public void createObjects(Context con) {
        speechRec = SpeechRecognizer.createSpeechRecognizer(con); // Initialise the recogniser
        // Specify that the intent will send the speech to the SpeechRecognizer to be recognised
        recIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Specify which language model to use. Free form is used here
        recIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Specify the maximum number of results to return
        recIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        // Set up the behaviours for the RecognitionListener
        RecognitionListener recListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                speechRec.stopListening();
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> results;
                // Get the result of recognition
                results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                // Use the first (only) result
                String output = results.get(0);
                displaySpeechResult(output);
                showTrigger();
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        };
        // Set the RecognitionListener to be used with the SpeechRecognizer
        speechRec.setRecognitionListener(recListener);
    }
    // Specify which TextView to use
    public void setSpeechOutput(TextView output) {
        this.speechOutput = output;
    }
    // Specify which ImageView to use
    public void setSpeechTrigger(Button trigger) {
        this.speechTrigger = trigger;
    }
    // Check if the app has permission to record audio and requests it if not
    public void checkPermission(Context con, Activity act)
    {
        if (ActivityCompat.checkSelfPermission(con, Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(act,
                new String[]{Manifest.permission.RECORD_AUDIO}, AUDIO_PERMISSION);
    }
    // Start listening for user speech
    public void startListening() {
        fadeTrigger();
        speechRec.startListening(recIntent);
    }
    // Displays the output using the TextView speechOutput
    public void displaySpeechResult(String output) {
        speechOutput.setText(output);
    }
    // Display the trigger
    public void showTrigger() {
        speechTrigger.setAlpha(1f);
    }
    // Fade the trigger to imply recording has started
    public void fadeTrigger() {
        speechTrigger.setAlpha(0.5f);
    }
    // Hide the trigger completely
    public void hideTrigger() {
        speechTrigger.setAlpha(0f);
    }
}

