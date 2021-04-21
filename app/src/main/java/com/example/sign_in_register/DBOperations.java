/* This class provides database operations that run in the background.
 * When a DBOperations object is created, the context and method need to be provided.
 * Methods can be found in doInBackground.
 * Right now, it will only work with a locally hosted server with the right file and db structure,
 * Author: Seán Coll
 * Created: 8/4/21
 * Last Edited: 14/4/21
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
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
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
        NetworkInterface check;
    }

    @Override
    protected String doInBackground(String... strings) {
        // Initialise url variables
        String ROOT_URL = "http://192.168.1.7/Sign_In_Register/";
//        String ROOT_URL = "http://192.168.1.7/Attendance/";
        String DATA_URL = "";
        // Check what method is to be run
        // This method will get all first names and surnames for the date specified
        if (this.method.equals("getDataForDate")) {
            try {
                String date = strings[0];
                DATA_URL = ROOT_URL + "getDataForDate.php";
                // Create URL object
                URL url = new URL(DATA_URL);
                // Create a URL connection
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                // Specify the type of request
                httpURLConnection.setRequestMethod("POST");
                // Allow output (Sending data from client)
                httpURLConnection.setDoOutput(true);
                // Accept the output through the OutputStream
                OutputStream OS = httpURLConnection.getOutputStream();
                // Buffered Writer used to apply parameters (none in this method)
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, StandardCharsets.UTF_8));
                // Encode the data to be sent
                String data = URLEncoder.encode("date","UTF-8")+"="+
                        URLEncoder.encode(date,"UTF-8");
                // Write the data to the BufferedWriter
                bufferedWriter.write(data);
                // Flush the BufferedWriter
                bufferedWriter.flush();
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
                return "Server is unavailable";
            }
        }
        // This method will sign the user in
        else if(this.method.equals("signIn")) {
            try {
                String fname = strings[0];
                String sname = strings[1];
                String date = strings[2];
//                String name = strings[0];
//                String date = strings[1];
                DATA_URL = ROOT_URL + "signIn.php";
                // Create URL object
                URL url = new URL(DATA_URL);
                // Create a URL connection
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                // Specify the type of request
                httpURLConnection.setRequestMethod("POST");
                // Allow output (Sending data from client)
                httpURLConnection.setDoOutput(true);
                // Accept the output through the OutputStream
                OutputStream OS = httpURLConnection.getOutputStream();
                // Buffered Writer used to apply parameters (none in this method)
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, StandardCharsets.UTF_8));
                // Encode the data to be sent
                String data = URLEncoder.encode("fname","UTF-8")+"="+
                        URLEncoder.encode(fname, "UTF-8")+"&"+
                        URLEncoder.encode("sname", "UTF-8")+"="+
                        URLEncoder.encode(sname, "UTF-8")+"&"+
                        URLEncoder.encode("date","UTF-8")+"="+
                        URLEncoder.encode(date,"UTF-8");
//                String data = URLEncoder.encode("name","UTF-8")+"="+
//                        URLEncoder.encode(name, "UTF-8")+"&"+
//                        URLEncoder.encode("date","UTF-8")+"="+
//                        URLEncoder.encode(date,"UTF-8");
                // Write the data to the BufferedWriter
                bufferedWriter.write(data);
                // Flush the BufferedWriter
                bufferedWriter.flush();
                // Close the BufferedWriter
                bufferedWriter.close();
                // Close the OutputStream
                OS.close();
                // Create InputStream to receive data from server
                InputStream IS = httpURLConnection.getInputStream();
                // Capture the data return from server
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, StandardCharsets.ISO_8859_1));
                // Close the InputStream
                IS.close();
                // Return the JSON string
                return "Login Successful";
            } catch (IOException e) {
                e.printStackTrace();
                return "Server is unavailable";
            }
        }
        // Something went wrong along the way the server is probably down.
        return "ERROR. Server is unavailable.";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
    }
}