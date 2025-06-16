package com.example.CDWeb.model;

public class SizeResponse {
    private Long id;

    private int value;

    public SizeResponse(Long id, int value) {
        this.id = id;
        this.value = value;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
