package com.example.ee408project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.sql.SQLException;

public class CheckOut extends AppCompatActivity {

    private StoreDatabase dbHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);

        final TextView fname = (TextView)findViewById(R.id.fname);
        final TextView lname = (TextView)findViewById(R.id.lname);
        final TextView street1 = (TextView)findViewById(R.id.street1);
        final TextView street2 = (TextView)findViewById(R.id.street2);
        final TextView city = (TextView)findViewById(R.id.city);
        final TextView state = (TextView)findViewById(R.id.state);
        final TextView zip = (TextView)findViewById(R.id.zip);
        final Spinner country = (Spinner) findViewById(R.id.country);
        final Spinner cardtype = (Spinner)findViewById(R.id.cardtype);
        final TextView number = (TextView)findViewById(R.id.number);
        final TextView cvc = (TextView)findViewById(R.id.cvc);
        final TextView expiration = (TextView)findViewById(R.id.expiration);


        Button confirm = (Button)findViewById(R.id.confirm);
        assert confirm != null;
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Person user = new Person(fname.getText().toString(), lname.getText().toString(), street1.getText().toString(), street2.getText().toString(), city.getText().toString(), state.getText().toString(), zip.getText().toString(), country.getSelectedItem().toString());
                    user.updateCard(cardtype.getSelectedItem().toString(), number.getText().toString(), cvc.getText().toString(), expiration.getText().toString());
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(CheckOut.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(CheckOut.this, "Order Confirmed. Thanks for shopping with us", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

