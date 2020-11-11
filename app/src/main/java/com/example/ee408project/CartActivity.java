package com.example.ee408project;


import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.math.BigDecimal;
import java.sql.SQLException;


public class CartActivity extends AppCompatActivity {

    private StoreDatabase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart);

        dbHelper = new StoreDatabase(this);
        try {
            dbHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int total = dbHelper.getTotalItemsCount();
        int num = dbHelper.getCartItemsRowCount(1);
        int amount = dbHelper.getAmount();
        BigDecimal priceVal;
        if (total == num){
            double tAmount = amount - (0.2 * amount);
            priceVal = BigDecimal.valueOf((long) tAmount, 2);
        } else {
            priceVal = BigDecimal.valueOf(amount, 2);
        }


        TextView numItemsBought = (TextView)findViewById(R.id.cart);
        numItemsBought.setText(num+" of "+ total+" items");

        TextView totalAmount = (TextView)findViewById(R.id.total);
        totalAmount.setText("Total Amount: $"+priceVal);

        LinearLayout cart = (LinearLayout)findViewById(R.id.linearLayout);
        Button checkout = (Button)findViewById(R.id.checkout);
        assert checkout != null;
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CheckOut.class);
                startActivity(intent);
            }
        });

        assert cart != null;
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clean all data
                dbHelper.deleteAllItems();
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        //Generate ListView from SQLite Database
        displayListView();

    }

    private void displayListView() {
        Cursor cursor = dbHelper.fetchAllItems("1"); // 1 is used to denote an item in the shopping cart

        // Display name of item to be bought
        String[] columns = new String[] {
                StoreDatabase.KEY_NAME
        };

        // the XML defined view which the data will be bound to
        int[] to = new int[] {
                R.id.name,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(
                this, R.layout.itemlayout,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView);
        // Assign adapter to ListView
        assert listView != null;
        listView.setAdapter(dataAdapter);
    }

}
