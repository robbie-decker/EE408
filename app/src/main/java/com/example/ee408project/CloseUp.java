package com.example.ee408project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.sql.SQLException;

public class CloseUp extends AppCompatActivity {

        private StoreDatabase dbHelper;
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.closeup);

            final Bundle bundle = getIntent().getExtras();

            TextView name = (TextView)findViewById(R.id.name);
            //TextView description = (TextView)findViewById(R.id.description);
            TextView price = (TextView)findViewById(R.id.price);
            EditText quantity = (EditText)findViewById(R.id.quantity);

            assert name != null;
            name.setText(bundle.getString("name"));
            //assert description != null;
            //description.setText(bundle.getString("description"));
            BigDecimal priceVal = BigDecimal.valueOf(bundle.getInt("price"),2); // we had stored price as a whole integer to include cents e.g 1.00 was stored as 100
            assert price != null;
            price.setText("Price: $"+priceVal);

            dbHelper = new StoreDatabase(this);
            try {
                dbHelper.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Button button = (Button)findViewById(R.id.addtocart);
            assert button != null;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dbHelper.addToCart(bundle.getInt("_id"), "1")){
                        Intent intent = new Intent(CloseUp.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        Toast.makeText(CloseUp.this, "Successfully added to shopping cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CloseUp.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            Button buy = (Button)findViewById(R.id.buy);
            assert buy != null;
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dbHelper.addToCart(bundle.getInt("_id"), "1")){
                        Intent intent = new Intent(CloseUp.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        Toast.makeText(CloseUp.this, "Successfully added to shopping cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CloseUp.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
}
