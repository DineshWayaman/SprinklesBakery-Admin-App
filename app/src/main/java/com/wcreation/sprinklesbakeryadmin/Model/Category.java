package com.wcreation.sprinklesbakeryadmin.Model;

public class Category {
    private int c_id;
    private String cat_name;
    private String cat_desc;

    public Category(int c_id, String cat_name, String cat_desc) {
        this.c_id = c_id;
        this.cat_name = cat_name;
        this.cat_desc = cat_desc;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_desc() {
        return cat_desc;
    }

    public void setCat_desc(String cat_desc) {
        this.cat_desc = cat_desc;
    }
}
