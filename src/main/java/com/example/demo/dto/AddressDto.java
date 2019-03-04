package com.example.demo.dto;

public class AddressDto {
    private String street;
    private String city;
    private String province;
    private String postalCode;

    public AddressDto(String street, String city, String province, String postalCode) {
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }

    public AddressDto() {
    }

    public void setStreet(String street) {
        this.street = street;

    }

    public void setCity(String city) {
        this.city = city;

    }

    public void setProvince(String province) {
        this.province = province;

    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;

    }


    public String getStreet() {
        return street;
    }


    public String getCity() {
        return city;
    }


    public String getProvince() {
        return province;
    }


    public String getPostalCode() {
        return postalCode;
    }

    }

