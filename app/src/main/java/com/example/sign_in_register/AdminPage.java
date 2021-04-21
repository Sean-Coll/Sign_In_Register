package com.example.sign_in_register;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class AdminPage extends AppCompatActivity implements View.OnClickListener {

    ImageButton back;
    DBOperations getAll;
    CalendarView cal;
    ListView list;
    TextView noEntries;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpage);

        back = findViewById(R.id.Back_Arrow);
        back.setOnClickListener(this);
        getAll = new DBOperations(this, "getData");
        cal = findViewById(R.id.Calendar);
        list = findViewById(R.id.List);
        noEntries = findViewById(R.id.No_Entries);
        CalendarView.OnDateChangeListener dateListener = new CalendarView.OnDateChangeListener() {
            // Parameters: The calendar, year, month, day
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String month = "" + i1; // Change int to String
                String day = "" + i2; // Change int to String

                // Change the month to 0x if the month number is less than 10
                if(i1 < 10) {
                    month = "0" + (i1 + 1); // Add one to the number since it starts on 0
                }
                // Change the month to 0x if the day number is less than 10
                if(i2 < 10) {
                    day = "0" + i2;
                }
                // Concatenate the year, month and day
                String date = i + "-" + month + "-" + day;
                // Create object to get data for a specific date
                DBOperations getDataForDate = new DBOperations(getApplicationContext(), "getDataForDate");
                try {
                    // Execute the operation
                    String result = getDataForDate.execute(date).get();
                    // If the server is down
                    if(result.equals("Server is unavailable")) {
                        noEntries.setText(""); // Clear the TextView
                        list.setAdapter(null); // Clear the ListView
                        noEntries.setText(result);
                    }
                    // Otherwise there is data to parse
                    else {
                        parseData(result);
                    }
                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        // Set the listener for the calendar
        cal.setOnDateChangeListener(dateListener);
    }
    // Parses a JSON string return from a DB operation
    private void parseData(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] name = new String[jsonArray.length()]; // Array for names

        for(int i = 0; i < jsonArray.length(); i++) {
            // Create a new StringBuilder each time so it is clear
            JSONObject obj = jsonArray.getJSONObject(i);
            name[i] = obj.getString("name");
        }
        // Display all full names
        displayData(name);
    }

    private void displayData(String[] data) {
        // If there are no entries for a day
        if(data.length == 0) {
            noEntries.setText(""); // Clear the TextView
            noEntries.setText(R.string.no_entries);
            list.setAdapter(null); // Clear the ListView
        }
        else {
            noEntries.setText(""); // Clear the TextView
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            list.setAdapter(adapter); // Clear the ListView
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.Back_Arrow) {
            finish();
        }
    }
}
