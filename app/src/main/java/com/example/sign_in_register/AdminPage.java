package com.example.sign_in_register;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class AdminPage extends AppCompatActivity implements View.OnClickListener {

    ImageButton back;
    DBOperations getAll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpage);

        back = findViewById(R.id.Back_Arrow);
        back.setOnClickListener(this);
        getAll = new DBOperations(this, "getData");
    }

    // Parses a JSON string return from a DB operation
    // Right now it only gets the first name (fname) **REMOVE THIS LATER**
    private void parseData(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        StringBuilder sb = new StringBuilder();

        String[] people = new String[jsonArray.length()];

        for(int i = 0; i < jsonArray.length(); i++) {

            JSONObject obj = jsonArray.getJSONObject(i);
            people[i] = obj.getString("fname");
            sb.append(people[i]);
        }
        Toast.makeText(this, sb.toString().trim(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.Back_Arrow) {
            finish();
        }
    }
}
