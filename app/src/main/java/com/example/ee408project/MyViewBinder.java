package com.example.ee408project;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.math.BigDecimal;

public class MyViewBinder implements SimpleCursorAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        int viewID = view.getId();
        switch(viewID){

            case R.id.name:
                TextView Name = (TextView) view;
                String name;
                name = cursor.getString(cursor.getColumnIndex(StoreDatabase.KEY_NAME));
                Name.setText(name);
                break;
            case R.id.price:
                TextView Price = (TextView) view;
                int price;
                price = cursor.getInt(cursor.getColumnIndex(StoreDatabase.KEY_PRICE));
                BigDecimal priceVal = BigDecimal.valueOf((price), 2);
                Price.setText("Price: $"+priceVal);
                break;
            case R.id.description:
                TextView Description = (TextView) view;
                String description;
                description = cursor.getString(cursor.getColumnIndex(StoreDatabase.KEY_DESCRIPTION));
                Description.setText(description);
                break;
            case R.id.item__image:
                ImageView contactProfile = (ImageView) view;
                byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(StoreDatabase.KEY_IMAGE));
                if(imageBytes != null ){
                    // Pic image from database
                    contactProfile.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
                }else {
                    // If image not found in database , assign a default image
                    //contactProfile.setBackgroundResource(R.drawable.bubble_a);
                }
                break;
        }
        return true;
    }
}