package com.example.demo.model.entity;

import com.example.demo.dto.ContactDto;

import javax.persistence.*;

@Entity
public class RealCustomer extends  Customer{
    @Id
    @GeneratedValue
    private Integer  id;
    private String lastName;
    private  String nationalCode;
    @OneToOne(cascade = CascadeType.ALL)
    private Contact contact;

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
