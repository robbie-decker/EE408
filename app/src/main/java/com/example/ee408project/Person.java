package com.example.ee408project;

public class Person {

    //Personal Details
    public String f_name;
    public String l_name;
    public String street_1;
    public String street_2;
    public String city;
    public String state;
    public String zip;
    public String country;

    // Card Details
    public String card_type;
    public String number;
    public String cvc;
    public String expiration;

    // Default Constructor
    public Person(){
        this.f_name = "NOT SET";
    }

    // Constructor
    public Person(String fn, String ln, String s1, String s2, String city, String state, String zip, String country){
        this.f_name = fn;
        this.l_name = ln;
        this.street_1 = s1;
        this.street_2 = s2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    // This is a storage container class - information is readily available and editable using . notation, but functions added for ease of manipulation
    public void updateCard(String type, String num, String cvc, String expiration){
        this.card_type = type;
        this.number = num;
        this.cvc = cvc;
        this.expiration = expiration;
    }
}