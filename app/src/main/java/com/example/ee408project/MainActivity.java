package com.example.ee408project;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.SimpleCursorAdapter;
        import android.widget.Toast;

        import java.math.BigDecimal;
        import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private StoreDatabase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new StoreDatabase(this);
        //dbHelper.deleteAllItems();
        Bitmap thisshoe = BitmapFactory.decodeResource(getResources(), R.drawable.yeezy);
        Bitmap that = BitmapFactory.decodeResource(getResources(), R.drawable.jordans);
        Bitmap the = BitmapFactory.decodeResource(getResources(), R.drawable.heels);
        Bitmap those = BitmapFactory.decodeResource(getResources(), R.drawable.vans);
        try {
            dbHelper.open();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        int total = dbHelper.getTotalItemsCount();
        if(total<= 0){
            dbHelper.insertMyShopItems();
        }

        displayListView();
        //dbHelper.deleteAllItems();

        int num = dbHelper.getCartItemsRowCount(1);
        int amount = dbHelper.getAmount();
        BigDecimal priceVal;

        if(total == num){
            double tAmount = amount - (.2 * amount);
            priceVal = BigDecimal.valueOf((long) tAmount, 2);
        }
        else{
            priceVal = BigDecimal.valueOf(amount, 2);
        }
        Button cart = (Button)findViewById(R.id.cart);
        assert cart != null;
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }


    private void displayListView() {
        Cursor cursor = dbHelper.fetchAllItems("0"); // 0 is used to denote an item yet to be bought

        // Display name of item to be sold
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

                //byte[] imageByteArray = cursor.getBlob(7);
                //Bitmap image = BitmapFactory.decodeByteArray(imageByteArray,0, imageByteArray.length);

                Intent intent = new Intent(MainActivity.this, CloseUp.class);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("price", price);
                intent.putExtra("_id", itemId);
                //intent.putExtra("image" , image);
                startActivity(intent);

            }
        });
    }
}