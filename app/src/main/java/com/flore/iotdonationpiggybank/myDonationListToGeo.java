package com.flore.iotdonationpiggybank;

public class myDonationListToGeo {
    String insertCoin;
    String getMileage;
    String date;
    String location_change;

    public myDonationListToGeo(String insertCoin, String getMileage, String date, String location_change) {
        this.insertCoin = insertCoin;
        this.getMileage = getMileage;
        this.date = date;
        this.location_change = location_change;
    }

    public String getInsertCoin() {
        return insertCoin;
    }

    public void setInsertCoin(String insertCoin) {
        this.insertCoin = insertCoin;
    }

    public String getGetMileage() {
        return getMileage;
    }

    public void setGetMileage(String getMileage) {
        this.getMileage = getMileage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation_change() {
        return location_change;
    }

    public void setLocation_change(String location_change) {
        this.location_change = location_change;
    }
}
