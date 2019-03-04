package com.example.demo.dto;

import com.example.demo.dto.AddressDto;

public  class CustomerDto {



    private String name;
    private String customerId;
    private AddressDto address;


    public CustomerDto(String name, AddressDto address) {
        this.name = name;
        this.address = address;
    }

    public CustomerDto() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }


    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public AddressDto getAddress() {
        return address;
    }


}