package com.justin.perfect.royalestate.util;

public class Buyer {
    String location;
    String propertyType;
    String criteria;
    String acres;
    String sqft;
    String min;
    String max;
    String name;
    String date;
    String photo;
    String phone;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    String email;

    public Buyer(){}

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public Buyer(String name, String email, String location, String propertyType, String criteria, String min, String max, String date,String phone,String photo) {
        this.location = location;
        this.propertyType = propertyType;
        this.criteria = criteria;
        this.min = min;
        this.max = max;
        this.name = name;
        this.email = email;
        this.date = date;
        this.photo = photo;
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getAcres() {
        return acres;
    }

    public String getSqft() {
        return sqft;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public void setAcres(String acres) {
        this.acres = acres;
    }

    public void setSqft(String sqft) {
        this.sqft = sqft;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setMax(String max) {
        this.max = max;
    }
}
