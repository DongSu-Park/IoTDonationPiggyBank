package com.flore.iotdonationpiggybank;

public class giftListData {
    private int gift_img_resid; // 기프트콘 이미지
    private String gift_item_name; // 기프트콘 상품 이름
    private String gift_item_service; // 기프트콘 제공자 이름
    private int gift_item_point;

    public int getGift_img_resid() {
        return gift_img_resid;
    }

    public void setGift_img_resid(int gift_img_resid) {
        this.gift_img_resid = gift_img_resid;
    }

    public String getGift_item_name() {
        return gift_item_name;
    }

    public void setGift_item_name(String gift_item_name) {
        this.gift_item_name = gift_item_name;
    }

    public String getGift_item_service() {
        return gift_item_service;
    }

    public void setGift_item_service(String gift_item_service) {
        this.gift_item_service = gift_item_service;
    }

    public int getGift_item_point() {
        return gift_item_point;
    }

    public void setGift_item_point(int gift_item_point) {
        this.gift_item_point = gift_item_point;
    }
}
