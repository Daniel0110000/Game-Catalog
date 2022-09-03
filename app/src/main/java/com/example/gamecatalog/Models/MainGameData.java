package com.example.gamecatalog.Models;

public class MainGameData {

    private String id;
    private String name;
    private String description;
    private String image_all;

    public MainGameData(String id, String name, String description, String image_all) {
        this.name = name;
        this.description = description;
        this.image_all = image_all;
        this.id =  id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_all() {
        return image_all;
    }

    public void setImage_all(String image_all) {
        this.image_all = image_all;
    }
}
