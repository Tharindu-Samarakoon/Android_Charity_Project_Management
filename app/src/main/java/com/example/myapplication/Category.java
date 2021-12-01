package com.example.myapplication;

public class Category {

    private int catID;
    private String name;

    public Category() {};

    public Category(int catID, String name) {
        this.catID = catID;
        this.name = name;
    }

    public int getCatID() {
        return catID;
    }

    public void setCatID(int catID) {
        this.catID = catID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
