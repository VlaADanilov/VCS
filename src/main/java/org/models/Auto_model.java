package org.models;

import org.DB.MySQL_helper;

public class Auto_model {
    private int id;
    private int brand_id;
    private int user_id;
    private String model;
    private int year;
    private int price;
    private int mileage;
    private String city;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Auto_model(int brand_id, int user_id, String model, int year, int price, int mileage, String city, String description) {
        this.brand_id = brand_id;
        this.user_id = user_id;
        this.model = model;
        this.city=city;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.description=description;
    }

    public int getMileage() {
        return mileage;
    }
    public String getNiceMileage(){
        int count = 1;
        String result = "";
        String str = mileage + "";
        for (int i = str.length() - 1; i >= 0; i--) {
            result = str.charAt(i) + result;
            if (count % 3 ==  0 && i > 0) {
                result = " " + result;
            }
            count++;
        }
        return result;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Auto_model() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Марка: " + brand_id + " Модель: " + model + " Год выпуска: " + year + " Цена: " + price + " руб.";
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNicePrice(){
        int count = 1;
        String result = "";
        String str = price + "";
        for (int i = str.length() - 1; i >= 0; i--) {
            result = str.charAt(i) + result;
            if (count % 3 ==  0 && i > 0) {
                result = " " + result;
            }
            count++;
        }
        return result;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
