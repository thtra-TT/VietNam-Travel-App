package com.example.vntravelapp.models;

public class Tour {
    private String title;
    private String location;
    private String duration;
    private String price;
    private String description;
    private int imageResId;
    private String imageUrl; // Thêm trường imageUrl
    private float rating;
    private int reviewCount;

    public Tour(String title, String location, String duration, String price, String description, int imageResId, String imageUrl, float rating, int reviewCount) {
        this.title = title;
        this.location = location;
        this.duration = duration;
        this.price = price;
        this.description = description;
        this.imageResId = imageResId;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public String getDuration() { return duration; }
    public String getPrice() { return price; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
    public String getImageUrl() { return imageUrl; }
    public float getRating() { return rating; }
    public int getReviewCount() { return reviewCount; }
}
