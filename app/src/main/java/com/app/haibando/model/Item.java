package com.app.haibando.model;

//Class - Đối tượng ổ gà
public class Item {
    //Thuộc tính
    private Double Lat;
    private Double Lng;

    //Hàm khởi tạo không đối số
    public Item() {
    }

    //Hàm khởi tạo có đối số
    public Item(Double lat, Double lng) {
        Lat = lat;
        Lng = lng;
    }

    // Các hàm truyền và nhận dữ liệu
    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getLng() {
        return Lng;
    }

    public void setLng(Double lng) {
        Lng = lng;
    }
}
