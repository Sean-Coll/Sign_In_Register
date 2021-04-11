package com.example.sign_in_register;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpage);

        back = findViewById(R.id.Back_Arrow);
        back.setOnClickListener(this);
        getAll = new DBOperations(this, "getData");
        cal = findViewById(R.id.Calendar);
        list = findViewById(R.id.List);
        CalendarView.OnDateChangeListener dateListener = new CalendarView.OnDateChangeListener() {
            // Parameters: The calendar, year, month, day
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String month = "" + i1;
                String day = "" + i2;

                if(i1 < 10) {
                    month = "0" + (i1 + 1);
                }
                if(i2 < 10) {
                    day = "0" + i2;
                }
                String date = i + "-" + month + "-" + day; // Increase month by 1 since it starts at 0
//                Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();
                DBOperations getDataForDate = new DBOperations(getApplicationContext(), "getDataForDate");
                try {
                    String result = getDataForDate.execute(date).get();
                    parseData(result);
//                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        cal.setOnDateChangeListener(dateListener);
    }

    // Parses a JSON string return from a DB operation
    // Right now it only gets the first name (fname) **REMOVE THIS LATER**
    private void parseData(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        String[] fname = new String[jsonArray.length()];
        String[] sname = new String[jsonArray.length()];
        String[] fullName = new String[jsonArray.length()];

        for(int i = 0; i < jsonArray.length(); i++) {

            // Create a new StringBuilder each time so it is clear
            StringBuilder sb = new StringBuilder();
            JSONObject obj = jsonArray.getJSONObject(i);
            fname[i] = obj.getString("fname");
            sname[i] = obj.getString("sname");
            sb.append(fname[i]);
            sb.append(" "); // Add a space
            sb.append(sname[i]);
            fullName[i] = sb.toString().trim();
        }
        displayData(fullName);
    }

    private void displayData(String[] data) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        list.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.Back_Arrow) {
            finish();
        }
    }
}//
