package com.example.ee408project;

import android.graphics.Bitmap;

public class Item {

    String name, description, price, status;
    private Bitmap image;

    public Item(){}

    public Item(String name, String description, String price, String status, Bitmap image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public Bitmap getImage(){ return image; }

    public void setPrice(String price) {
        this.price = price;
    }
}