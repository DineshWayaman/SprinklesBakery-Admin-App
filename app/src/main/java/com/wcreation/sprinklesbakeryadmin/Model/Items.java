package com.wcreation.sprinklesbakeryadmin.Model;

public class Items {
    private int i_id;
    private String i_name;
    private String i_cat;
    private int i_qty;
    private double i_price;
    private String i_ingredients;
    private String i_message;
    private String i_img;

    public Items(int i_id, String i_name, String i_cat, int i_qty, double i_price, String i_ingredients, String i_message, String i_img) {
        this.i_id = i_id;
        this.i_name = i_name;
        this.i_cat = i_cat;
        this.i_qty = i_qty;
        this.i_price = i_price;
        this.i_ingredients = i_ingredients;
        this.i_message = i_message;
        this.i_img = i_img;
    }

    public int getI_id() {
        return i_id;
    }

    public void setI_id(int i_id) {
        this.i_id = i_id;
    }

    public String getI_name() {
        return i_name;
    }

    public void setI_name(String i_name) {
        this.i_name = i_name;
    }

    public String getI_cat() {
        return i_cat;
    }

    public void setI_cat(String i_cat) {
        this.i_cat = i_cat;
    }

    public int getI_qty() {
        return i_qty;
    }

    public void setI_qty(int i_qty) {
        this.i_qty = i_qty;
    }

    public double getI_price() {
        return i_price;
    }

    public void setI_price(double i_price) {
        this.i_price = i_price;
    }

    public String getI_ingredients() {
        return i_ingredients;
    }

    public void setI_ingredients(String i_ingredients) {
        this.i_ingredients = i_ingredients;
    }

    public String getI_message() {
        return i_message;
    }

    public void setI_message(String i_message) {
        this.i_message = i_message;
    }

    public String getI_img() {
        return i_img;
    }

    public void setI_img(String i_img) {
        this.i_img = i_img;
    }
}
