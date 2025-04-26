package com.example.CDWeb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String img;

    private double price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id") // tên cột trong bảng product
    private Category category;

//    @ManyToOne
//    @JoinColumn(name = "additionalImages", referencedColumnName = "id")
//    @JsonManagedReference
//    private AdditionalImages additionalImages;

    @ElementCollection
    @CollectionTable(name = "additionalImages", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "additionalImages")
    private List<String> additionalImages;


    @ElementCollection
    @CollectionTable(name = "product_size", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "size")
    private List<Integer> size;

    @ElementCollection
    @CollectionTable(name = "product_tint", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tint")
    private List<String> tint;




    // Getters and setters

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Integer> getSize() {
        return size;
    }

    public void setSize(List<Integer> size) {
        this.size = size;
    }

    public List<String> getTint() {
        return tint;
    }

    public void setTint(List<String> tint) {
        this.tint = tint;
    }

    public List<String> getAdditionalImages() {
        return additionalImages;
    }

    public void setAdditionalImages(List<String> additionalImages) {
        this.additionalImages = additionalImages;
    }
}
