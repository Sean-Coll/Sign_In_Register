/* This activity allows the user to tap the number on-screen to bring up their dialler with the
 * number already filled. The phone number used can be found in @string/SJOG_phone.
 * Author: Seán Coll
 * Created: 11/3/21
 * Last Edited: 12/3/21
 */

package com.example.sign_in_register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    TextView phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_page);

        back = findViewById(R.id.Back_Arrow);
        phoneNo = findViewById(R.id.Phone_No);
        back.setOnClickListener(this);
        phoneNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case(R.id.Back_Arrow): {
                finish();
                break;
            }

            case(R.id.Phone_No): {
                // Set the intent to dial the number, not call it
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                // Add the number to the intent to dial
                callIntent.setData(Uri.parse("tel:"+phoneNo.getText().toString()));
                startActivity(callIntent); // Dial the number
                break;
            }
        }
    }
}
