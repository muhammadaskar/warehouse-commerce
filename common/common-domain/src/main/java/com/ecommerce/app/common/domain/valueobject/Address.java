package com.ecommerce.app.common.domain.valueobject;

public class Address {
    private String street;
    private String postalCode;
    private String city;
    private String latitude;
    private String longitude;

    public Address(String street, String postalCode, String city, String latitude, String longitude) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
