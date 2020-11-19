package com.example.ee408project;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.SimpleCursorAdapter;

        import java.io.ByteArrayOutputStream;
        import java.math.BigDecimal;
        import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {
    private StoreDatabase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new StoreDatabase(this);

        Bitmap thisshoe = BitmapFactory.decodeResource(getResources(), R.drawable.yeezy);
        Bitmap that = BitmapFactory.decodeResource(getResources(), R.drawable.jordans);
        Bitmap the = BitmapFactory.decodeResource(getResources(), R.drawable.heels);
        Bitmap those = BitmapFactory.decodeResource(getResources(), R.drawable.vans);
        Bitmap crocs = BitmapFactory.decodeResource(getResources(), R.drawable.crocs);
        Bitmap mags = BitmapFactory.decodeResource(getResources(), R.drawable.mags);
        Bitmap slides = BitmapFactory.decodeResource(getResources(), R.drawable.slides);
        Bitmap timbs = BitmapFactory.decodeResource(getResources(), R.drawable.timbs);


        try {
            dbHelper.open();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //dbHelper.deleteAllItems();                    //sometimes this line has to be uncommented to reset the database

//        dbHelper.insertImage(getBytes(thisshoe));
//        dbHelper.insertImage(getBytes(that));
//        dbHelper.insertImage(getBytes(the));
//        dbHelper.insertImage(getBytes(those));
        if(dbHelper.getTotalItemsCount() <= 0) {
            dbHelper.deleteAllItems();
            dbHelper.createItem("Yeezy Boost", "Comfortable shoes for walking around\n", 6999, "0", thisshoe);
            dbHelper.createItem("Retro Jordans", "Athletic shoes for sports", 3999, "0", that);
            dbHelper.createItem("High Heels", "Heels for a night out", 9999, "0", the);
            dbHelper.createItem("High Top Vans", "Cool looking white shoes", 4499, "0", those);
            dbHelper.createItem("Crocs", "Comfy easy to wear rubber shoes", 999, "0", crocs);
            dbHelper.createItem("Nike Air Mags", "Self lacing shoes inspired by Back to The Future", 99999, "0", mags);
            dbHelper.createItem("Nike Slides", "Athletic slides for after sports", 2999, "0", slides);
            dbHelper.createItem("Timberland Boots", "Rugged boots for harsh weather", 9499, "0", timbs);
        }

        int total = dbHelper.getTotalItemsCount();
        //if(total<= 0){
        //    dbHelper.insertMyShopItems(getBytes(thisshoe));
        //}

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
                //StoreDatabase.KEY_NAME, StoreDatabase.KEY_IMAGE
                "name", "image", "price", "description"
        };
        Log.d("------------------------------------2", StoreDatabase.KEY_NAME);

        // the XML defined view which the data will be bound to
        int[] to = new int[] {
                R.id.name, R.id.item__image,R.id.price, R.id.description
        };


        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
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
                //Cursor cursor = dbHelper.rawQuery(sqlQuery, null);
                // Get the item attributes to be sent to CloseUp activity from this row in the database.
                String name =  cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description =  cursor.getString(cursor.getColumnIndexOrThrow("description"));
                int price =  cursor.getInt(cursor.getColumnIndexOrThrow("price"));
                int itemId =  cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
                Log.d("--------------------------------1", String.valueOf(imageByteArray));
                //Bitmap image = getImage(imageByteArray);
                Log.d("--------------------------------10", String.valueOf(imageByteArray));
                Intent intent = new Intent(MainActivity.this, CloseUp.class);
                intent.putExtra("name", name);
                Log.d("------------------------------------2", name);
                intent.putExtra("description", description);
                intent.putExtra("price", price);
                intent.putExtra("_id", itemId);
                intent.putExtra("image" , imageByteArray);
                //intent.putExtra("image" , image);
                Log.d("------------------------------------2", String.valueOf(imageByteArray));
                startActivity(intent);

            }
        });
    }
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Log.d("--------------------------------11", "1");
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        Log.d("--------------------------------12", "2");
        return stream.toByteArray();
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


}