package com.flore.iotdonationpiggybank.model;

import android.content.Context;
import android.location.Geocoder;

public class MyDonationList {
    String date;
    String getMileage;
    String insertCoin;
    String location_lat;
    String location_lng;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGetMileage() {
        return getMileage;
    }

    public void setGetMileage(String getMileage) {
        this.getMileage = getMileage;
    }

    public String getInsertCoin() {
        return insertCoin;
    }

    public void setInsertCoin(String insertCoin) {
        this.insertCoin = insertCoin;
    }

    public String getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(String location_lat) {
        this.location_lat = location_lat;
    }

    public String getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(String location_lng) {
        this.location_lng = location_lng;
    }
}
