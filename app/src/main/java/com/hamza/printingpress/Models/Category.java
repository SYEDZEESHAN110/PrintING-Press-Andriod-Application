package com.hamza.printingpress.Models;

import com.hamza.printingpress.Api.URLs;

public class Category {
    int id;
    String category_image;
    String name;

    public Category(int id, String category_image, String name) {
        this.id = id;
        this.category_image = URLs.imageUrl + category_image;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
