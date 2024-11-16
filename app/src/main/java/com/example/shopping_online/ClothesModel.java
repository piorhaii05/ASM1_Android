package com.example.shopping_online;

public class ClothesModel {

    String _id, name, date, brand;
    int price;

    public ClothesModel(int price, String name, String date, String brand, String _id) {
        this.price = price;
        this.name = name;
        this.date = date;
        this.brand = brand;
        this._id = _id;
    }

    public ClothesModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
