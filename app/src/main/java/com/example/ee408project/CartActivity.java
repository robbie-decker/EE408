package com.example.ee408project;


import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        Button clearcart = (Button)findViewById(R.id.clear);
        Button checkout = (Button)findViewById(R.id.checkout);
        assert checkout != null;
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CheckOut.class);
                startActivity(intent);
                finish();
            }
        });

        assert clearcart != null;
        clearcart.setOnClickListener(new View.OnClickListener() {
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
                //StoreDatabase.KEY_NAME, StoreDatabase.KEY_IMAGE
                "name", "image", "price", "description"
        };
        Log.d("------------------------------------2", StoreDatabase.KEY_NAME);

        // the XML defined view which the data will be bound to
        int[] to = new int[] {
                R.id.name, R.id.item__image,R.id.price, R.id.description
        };
        // create the adapter using the cursor pointing to the desired data
        // as well as the layout information
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(
                this, R.layout.itemlayout,
                cursor,
                columns,
                to,
                0);

        dataAdapter.setViewBinder( new MyViewBinder());
        ListView listView = (ListView) findViewById(R.id.listView);
        // Assign adapter to ListView
        assert listView != null;

        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the item attributes to be sent to CloseUp activity from this row in the database.
                String name =  cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description =  cursor.getString(cursor.getColumnIndexOrThrow("description"));
                int price =  cursor.getInt(cursor.getColumnIndexOrThrow("price"));
                int itemId =  cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));


                Intent intent = new Intent(CartActivity.this, CloseUp.class);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("price", price);
                intent.putExtra("_id", itemId);
                intent.putExtra("image" , imageByteArray);
                startActivity(intent);

            }
        });

    }

}
