package com.example.vntravelapp.models;

public class Tour {
    private String title;
    private String location;
    private String duration;
    private String price;
    private String imageUrl;
    private int imageResId = 0;
    private float rating;
    private int reviewCount;

    public Tour(String title, String location, String duration, String price, String imageUrl, float rating, int reviewCount) {
        this.title = title;
        this.location = location;
        this.duration = duration;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

    public Tour(String title, String location, String duration, String price, int imageResId, float rating, int reviewCount) {
        this.title = title;
        this.location = location;
        this.duration = duration;
        this.price = price;
        this.imageResId = imageResId;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public String getDuration() { return duration; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public int getImageResId() { return imageResId; }
    public float getRating() { return rating; }
    public int getReviewCount() { return reviewCount; }
}