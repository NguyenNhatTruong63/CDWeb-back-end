package com.example.CDWeb.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductResponse {
    private int id;
    private String name;
    private String img;
    private double price;
    private String description;
    private String category;
    private List<String> additionalImages;
    private List<Map<String, Object>> sizes;
    private List<Map<String, Object>> colors;
    
    // Getters và Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getAdditionalImages() {
        return additionalImages;
    }

    public void setAdditionalImages(List<String> additionalImages) {
        this.additionalImages = additionalImages;
    }

    public List<Map<String, Object>> getSizes() {
        return sizes;
    }

    public void setSizes(List<Map<String, Object>> sizes) {
        this.sizes = sizes;
    }

    public List<Map<String, Object>> getColors() {
        return colors;
    }

    public void setColors(List<Map<String, Object>> colors) {
        this.colors = colors;
    }
}
