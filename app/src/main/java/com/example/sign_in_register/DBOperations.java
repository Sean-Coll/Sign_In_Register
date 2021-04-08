/* This class provides database operations that run in the background.
 * When a DBOperations object is created, the context and method need to be provided.
 * Methods can be found in doInBackground.
 * Right now, it will only work with a locally hosted server with the right file and db structure,
 * Author: Seán Coll
 * Created: 8/4/21
 * Last Edited: 8/4/21
 */

package com.example.sign_in_register;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DBOperations extends AsyncTask<String,Void,String> {

    String method;
    Context con;

    // Constructor
    DBOperations(Context con, String method) {
        this.con = con;
        this.method = method;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        // URL to the getData PHP script which gets all the data
        String DATA_URL = "http://192.168.1.7/Sign_In_Register/getData.php";
        // Check what method is to be run
        if (this.method.equals("getData")) {
            try {
                // Create URL object
                URL url = new URL(DATA_URL);
                // Create a URL connection
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                // Specify the type of request
                httpURLConnection.setRequestMethod("GET");
                // Allow output (Sending data from client)
                httpURLConnection.setDoOutput(true);
                // Accept the output through the OutputStream
                OutputStream OS = httpURLConnection.getOutputStream();
                // Buffered Writer used to apply parameters (none in this method)
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, StandardCharsets.UTF_8));
                // Close the BufferedWriter
                bufferedWriter.close();
                // Close the OutputStream
                OS.close();
                // Create InputStream to receive data from server
                InputStream IS = httpURLConnection.getInputStream();
                // Capture the data return from server
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, StandardCharsets.ISO_8859_1));
                // Create StringBuilder to format the data
                StringBuilder sb = new StringBuilder();
                String json;
                // Append each line of data to a single string which will form the JSON string
                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json).append("\n");
                }
                // Close the InputStream
                IS.close();
                // Return the JSON string
                return sb.toString().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Something went wrong along the way usually an error it the PHP script.
        return "Didn't work :(";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
    }
}