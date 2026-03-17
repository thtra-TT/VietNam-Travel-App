package com.example.vntravelapp.models;

public class Trip {
    private int id;
    private String departureLocation;
    private String destinationLocation;
    private String departureDate;
    private String departureTime;
    private int availableSeats;
    private String brandName;
    private String price;
    private String status; // upcoming, completed, cancelled

    public Trip(int id, String departureLocation, String destinationLocation, String departureDate, String departureTime, int availableSeats, String brandName, String price) {
        this.id = id;
        this.departureLocation = departureLocation;
        this.destinationLocation = destinationLocation;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.availableSeats = availableSeats;
        this.brandName = brandName;
        this.price = price;
        this.status = "upcoming";
    }

    public Trip(int id, String departureLocation, String destinationLocation, String departureDate, String departureTime, int availableSeats, String brandName, String price, String status) {
        this(id, departureLocation, destinationLocation, departureDate, departureTime, availableSeats, brandName, price);
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getDepartureLocation() { return departureLocation; }
    public String getDestinationLocation() { return destinationLocation; }
    public String getDepartureDate() { return departureDate; }
    public String getDepartureTime() { return departureTime; }
    public int getAvailableSeats() { return availableSeats; }
    public String getBrandName() { return brandName; }
    public String getPrice() { return price; }
    public String getStatus() { return status; }
    
    // Setter
    public void setStatus(String status) { this.status = status; }
}
