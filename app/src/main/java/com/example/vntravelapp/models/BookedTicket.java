package com.example.vntravelapp.models;

public class BookedTicket {
    private int id;
    private Trip trip;
    private String customerName;
    private String pickupPoint;
    private String customerPhone;

    public BookedTicket(int id, Trip trip, String customerName, String pickupPoint, String customerPhone) {
        this.id = id;
        this.trip = trip;
        this.customerName = customerName;
        this.pickupPoint = pickupPoint;
        this.customerPhone = customerPhone;
    }

    public int getId() { return id; }
    public Trip getTrip() { return trip; }
    public String getCustomerName() { return customerName; }
    public String getPickupPoint() { return pickupPoint; }
    public String getCustomerPhone() { return customerPhone; }
}
