package com.example.ee408project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        final Bundle bundle = getIntent().getExtras();

        TextView fname = (TextView)findViewById(R.id.fname);
        TextView lname = (TextView)findViewById(R.id.lname);
        TextView street1 = (TextView)findViewById(R.id.street1);
        TextView street2 = (TextView)findViewById(R.id.street2);
        TextView city = (TextView)findViewById(R.id.city);
        TextView state = (TextView)findViewById(R.id.state);
        TextView zip = (TextView)findViewById(R.id.zip);
        TextView country = (TextView)findViewById(R.id.country);
        TextView cardtype = (TextView)findViewById(R.id.cardtype);
        TextView number = (TextView)findViewById(R.id.number);
        TextView cvc = (TextView)findViewById(R.id.cvc);
        TextView expiration = (TextView)findViewById(R.id.expiration);

        Person user = new Person(fname.getText().toString(), lname.getText().toString(), street1.getText().toString(), street2.getText().toString(), city.getText().toString(), state.getText().toString(), zip.getText().toString(), country.getText().toString());
        user.updateCard(cardtype.getText().toString(), number.getText().toString(), cvc.getText().toString(),  expiration.getText().toString());

        //assert name != null;
        //name.setText(bundle.getString("name"));
        //assert description != null;
        //description.setText(bundle.getString("description"));
//        BigDecimal priceVal = BigDecimal.valueOf(bundle.getInt("price"),2); // we had stored price as a whole integer to include cents e.g 1.00 was stored as 100
//        assert price != null;
//        price.setText("Price: $"+priceVal);
//
//        dbHelper = new StoreDatabase(this);
//        try {
//            dbHelper.open();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        Button button = (Button)findViewById(R.id.buy);
//        assert button != null;
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dbHelper.addToCart(bundle.getInt("_id"), "1")){
//                    Intent intent = new Intent(CheckOut.this, MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
//                    Toast.makeText(CheckOut.this, "Successfully added to shopping cart", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(CheckOut.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
    }
}
