package com.flore.iotdonationpiggybank.model;

public class MyDevice {
    String id;
    Double lat;
    Double lng;
    int totalMoney;
    int waitCoin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getWaitCoin() {
        return waitCoin;
    }

    public void setWaitCoin(int waitCoin) {
        this.waitCoin = waitCoin;
    }
}
