package com.example.gamecatalog.Models;

public class Categories {

    private String category;
    private int img_category;

    public Categories(String category, int img_category) {
        this.category = category;
        this.img_category = img_category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImg_category() {
        return img_category;
    }

    public void setImg_category(int img_category) {
        this.img_category = img_category;
    }
}
